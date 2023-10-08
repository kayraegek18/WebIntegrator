# WebIntegrator
Is a simple library for integrating a website written in nodejs with your minecraft server.

### Node.js
```bash
npm install webintegrator@latest
```
```js
const WebIntegrator =  require('webintegrator');

const  client  =  new  WebIntegrator(1234);

client.onData  = (data) => {
	// JSON Data
	// { code: 0, response: 'A Minecraft Server' }
	console.log(data);
};

client.onConnected  = () => {
	console.log("Connected");
	client.send(
		// OPERATION CODE FOR SERVER
		0, 
		// AUTO CONVERTING TO JSON
		{
			player: 'test'
		}
	);
};

client.onClose  = () => {
	console.log("Disconnected");
};

client.onError  = (err) => {
	console.log("Error: "  +  err);
}
```
### Java (Minecraft Plugin)
```xml
<repository>  
	<id>mineala-repo</id>  
	<url>https://repo.mineala.com/maven2</url>  
</repository>

<dependency>  
	<groupId>net.kayega</groupId>  
	<artifactId>webintegrator</artifactId>  
	<version>VERSION</version>  
</dependency>
```
```java
public final class WebIntegratorTest extends JavaPlugin {  

	WebIntegrator.CreateServerReturnParams params;

	@Override  
	public void onEnable() {  
		// Creating server (127.0.0.1:1234)
		params = WebIntegrator.getInstance().createServer(1234);
		  
		// Register your own packet
		WebIntegrator.getInstance().registerPacket(new WebPacket(0, new WebPacket.WebPacketCallback() {  
			@Override  
			public String run(JSONObject jsonData) {  
				return "RESPONSE FOR CLIENT";  
			}  
		}));  
	}  
	  
	@Override  
	public void onDisable() {  
		// Stopping server
		params.getServerRunnable().close(); 
	}  
}
```
