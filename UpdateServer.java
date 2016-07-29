import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

class UpdateServer extends TimerTask {
	static String coordinate;
	public static String[] coordinates;
	private boolean panic;
	private String dangerZone;
	private String uniqueId = "12345";
	public static int position;
	
	static{
		try {
			coordinates = LoadGithubContent.getCoordinates().split("\n");
		} catch (Throwable e) {
		}
	}
	
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
    	coordinate = coordinates[position++].split(",")[1]+","+coordinates[position++].split(",")[0];
    	System.out.println("Hello World! act=track&coordinate="+coordinate+"&panic="+panic+"&danger="+danger+"&dangerZone="+dangerZone+"&id="+uniqueId);
    	String response = hitServer();
    	if(response != null){
    	   String res = response.substring(1, response.length()-1);
    	   this.danger = "true".equals(res.split(":")[1]);
    	   System.out.println("panic:"+panic);
    	   System.out.println("danger:"+danger);
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

		String url = "http://192.168.0.101:8080/map/SimpleServlet?act=track&coordinate="+coordinate+"&panic="+panic+"&danger="+danger+"&dangerZone="+dangerZone+"&id="+uniqueId;
		
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