import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

class UpdateServer extends TimerTask {
	private String coordinate;
	private boolean panic;
	private String dangerZone;
	private String uniqueId = "12345";
	
	public boolean isPanic() {
		return panic;
	}

	public void setPanic(boolean panic) {
		this.panic = panic;
	}

	private boolean danger;
	public boolean isDanger() {
		return danger;
	}

	public void setDanger(boolean danger) {
		this.danger = danger;
	}

	public String getDangerZone() {
		return dangerZone;
	}

	public void setDangerZone(String dangerZone) {
		this.dangerZone = dangerZone;
	}
	
    public void run() {
       System.out.println("Hello World! coordinate="+coordinate+"&panic="+panic+"&danger="+danger+"&dangerZone="+dangerZone+"&uniqueId="+uniqueId);
       String response = hitServer();
       if(response != null){
    	   
       }
    }
    
    public void updateValues(String coordinate, boolean panic, boolean danger, String dangerZone){
    	this.coordinate = coordinate;
    	this.panic = panic;
    	this.danger = danger;
    	this.dangerZone = dangerZone;
    }
    
	public String hitServer(){
		System.out.println("Send Http GET request");

		String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/ajaxGetQuoteJSON.jsp?symbol=&series=EQ";
		
		URL obj;
		HttpURLConnection con = null;
		try {
			obj = new URL(url);
		
		con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		// optional default is GET
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode;
		StringBuffer response;
		try {
			responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		} catch (IOException e) {
			return null;
		}

		//print result
		System.out.println(response.toString());
		return response.toString();

	}
	}