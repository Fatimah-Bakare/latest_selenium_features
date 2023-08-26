import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.Request;
import org.openqa.selenium.devtools.v114.network.model.Response;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NetworkLogActivity {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		
		//log file ->
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.addListener(Network.requestWillBeSent(), request -> 
		{
			Request req = request.getRequest();
			System.out.println(req.getUrl());
			//req.getHeaders();
		});

		//Event will get fired -
		devTools.addListener(Network.responseReceived(), response -> 
		{
			Response res = response.getResponse();
			//System.out.println(res.getUrl());
			System.out.println(res.getStatus());
			if(res.getStatus().toString().startsWith("4")) 
			{
				System.out.println(res.getUrl() + "is failing with status code: " + res.getStatus());
			}
		});
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*='library']")).click();
		

	}

}
