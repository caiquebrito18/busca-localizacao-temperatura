package com.temperatura.gerenciador.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temperatura.gerenciador.dtos.Cidade;
import com.temperatura.gerenciador.dtos.ClienteDto;
import com.temperatura.gerenciador.dtos.Clima;
import com.temperatura.gerenciador.dtos.ClimaConsolidado;
import com.temperatura.gerenciador.dtos.IP;
import com.temperatura.gerenciador.entities.Cliente;
import com.temperatura.gerenciador.responses.Response;
import com.temperatura.gerenciador.service.ClienteService;



@RestController
@RequestMapping("api/clientes")
public class GerenciadorLocalizacaoTemperaturaController {

	private static final String METAWEATHER_LOCATION = "https://www.metaweather.com/api/location/";
	private static final String HTTPS_METAWEATHER_LAT_LONG = "https://www.metaweather.com/api/location/search/?lattlong=";
	private static final String IPVIGILANTE = "https://ipvigilante.com/";
	@Autowired
	private ClienteService clienteService;

	@PostMapping(path = "/cadastrar")
	public ResponseEntity<Response> cadastrar(@Valid @RequestBody ClienteDto clienteDto) {
		
		try {
			
			InetAddress inetAddress = InetAddress.getLocalHost();
	        System.out.println("IP Address:- " + inetAddress.getHostAddress());
			
	        ObjectMapper mapper = new ObjectMapper();
	        
	        String jSon = chamarServico(IPVIGILANTE+"189.40.89.168");
			
			IP ipVigilante = mapper.readValue(jSon, IP.class);
			
			clienteDto.setLatitude(ipVigilante.getData().getLatitude());
			clienteDto.setLongitude(ipVigilante.getData().getLongitude());
						
			jSon = chamarServico(HTTPS_METAWEATHER_LAT_LONG+ipVigilante.getData().getLatitude()+","+ipVigilante.getData().getLongitude());
			
			List<Cidade> listaCidades = Arrays.asList(mapper.readValue(jSon, Cidade[].class));
			String woeid = null;
			
			if(listaCidades != null && !listaCidades.isEmpty()){
				for(Cidade cidade : listaCidades){
					if(ipVigilante.getData().getCity_name().equals(cidade.getTitle())){
						woeid = cidade.getWoeid();
						break;
					}
				}
			}
			
			if(woeid != null){
				
				jSon = chamarServico(METAWEATHER_LOCATION+woeid);
				Clima clima = mapper.readValue(jSon, Clima.class);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date hoje = new Date();
				
				for(ClimaConsolidado climaConsolidado : clima.getConsolidated_weather()){
					if(climaConsolidado.getApplicable_date().equals(sdf.format(hoje))){
						
						clienteDto.setTempMin(climaConsolidado.getMin_temp());
						clienteDto.setTempMax(climaConsolidado.getMax_temp());
						break;
					}
				}
			}
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Ocorreu um erro no momento de salvar o cliente.",null));
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clienteDto = clienteService.salvar(clienteDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Cliente cadastrado",clienteDto));
	}

	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		List<Cliente> clientes = clienteService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	
	@GetMapping(path = "/buscar/{id}")
	public ResponseEntity<Response> buscar(@PathVariable Long id) {
		
		try {			
			ClienteDto clienteDto = clienteService.buscar(id);
			
			if(clienteDto == null || clienteDto.getId() == null){
				return ResponseEntity.status(HttpStatus.OK).body(new Response("Pessoa nao encontrada",clienteDto));
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Pessoa encontrada com sucesso",clienteDto));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Erro ao buscar a pessoa",null));
		}
	}
	
	@PutMapping(path = "/alterar")
	public ResponseEntity<Response> alterar(@RequestBody ClienteDto clienteDto) {

		if(clienteDto == null || clienteDto.getId() == null){
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Informe o ID do cliente",null));
		}else{

			Cliente cliente = this.clienteService.alterar(clienteDto);

			if(cliente == null){
				return ResponseEntity.status(HttpStatus.OK).body(new Response("Nao existe um cliente com esse ID.",null));
			}

			return ResponseEntity.status(HttpStatus.OK).body(new Response("Cliente alterado com sucesso",clienteDto));
		}

	}
	
	@GetMapping(path = "/delete/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id) {
		
		if(id == null){
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Informe o ID do cliente",null));
		}else{	
			ClienteDto clienteDto = clienteService.deletar(id);
			
			if(clienteDto == null){
				return ResponseEntity.status(HttpStatus.OK).body(new Response("Nao existe um cliente com esse ID.",null));
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Cliente deletado com sucesso",clienteDto));
		}
	}
	
	
	public String chamarServico(String urlServico) throws NoSuchAlgorithmException, KeyManagementException{
		
		try {
			
			/* Start of Fix */
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }

            } };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) { return true; }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            /* End of the fix*/
			
        	URL url = new URL(urlServico);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String output;
			
			String retorno = "";
			while ((output = br.readLine()) != null) {
				retorno+=output;
			}
			
			conn.disconnect();
			
			return retorno;
			
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
		}	
		
		return null;
	}	
	
}
