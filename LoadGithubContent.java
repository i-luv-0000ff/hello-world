import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
 
/**
 * @author Crunchify.com
 *
 */
 
public class LoadGithubContent {
 
	public static String getCoordinates() throws Throwable {
		String link = "https://raw.githubusercontent.com/i-luv-0000ff/hello-world/master/coordinates.txt";
		URL url = new URL(link);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		Map<String, List<String>> myHeader = http.getHeaderFields();
 
		// If URL is getting 301 and 302 redirection HTTP code then get new URL link.
		// This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
		for (String header : myHeader.get(null)) {
			if (header.contains(" 302 ") || header.contains(" 301 ")) {
				link = myHeader.get("Location").get(0);
				url = new URL(link);
				http = (HttpURLConnection) url.openConnection();
				myHeader = http.getHeaderFields();
			}
		}
		InputStream stream = http.getInputStream();
		String response = getStringFromStream(stream);
		return response;
	}
 
        // ConvertStreamToString() Utility - we name it as crunchifyGetStringFromStream()
	private static String getStringFromStream(InputStream stream) throws IOException {
		if (stream != null) {
			Writer writer = new StringWriter();
 
			char[] buffer = new char[2048];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				int counter;
				while ((counter = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, counter);
				}
			} finally {
				stream.close();
			}
			return writer.toString();
		} else {
			return "No Contents";
		}
	}
}