import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class IssOpenNotifyApi {

   String issAnswer;

    String howManyHumans() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet question = new HttpGet("http://api.open-notify.org/astros.json");
        HttpResponse answer = httpClient.execute(question);
        issAnswer = EntityUtils.toString(answer.getEntity());
        return issAnswer;
    }

    String whereIsNowIssRequest() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet question = new HttpGet("http://api.open-notify.org/iss-now.json");
        HttpResponse answer = httpClient.execute(question);
        if (answer.getStatusLine().getStatusCode() == 200) {
                /* most popular codes:
                200 OK, 201 Created, 204 No Content, 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found,
                500 Internal Server Error,502 Bad Gateway, 503 Service Unavailable, Gateway Timeout */
            issAnswer = EntityUtils.toString(answer.getEntity());
        } else {
            System.err.println("data loading error, code: " + answer.getStatusLine().getStatusCode());
        }
        return issAnswer;
    }
}