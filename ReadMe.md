Project Name: CakeManager Service
JDK Version: 17

###Description:
<p>This project serves RESTful API endpoints for Cakes data. The endpoints expose and download cake data, 
and there is an endpoint to save new cake data information.</p>
<p>The project makes use of the H2 in-memory database (which is only available when
application is running),
and the required libraries have been included in the application pom file, so no
additional configuration is required. Sample data is loaded during application start-up
and this data is wiped every time the application is shutdown.</p>

###Improvements:
<p>I have recreated the project using Spring-boot and exposed all endpoints using RestFul protocols.
All project dependencies are being pulled in using Maven and read from the pom file. The 
project is self-contained, and you only need to have JDK in your classpath to 
be able to run the application. You will also need to install Docker for the containerization.</p>

###Run Instructions:
In your terminal application go to the root directory of the application after downloading it. In
the root directory run this command <em>./mvnw install </em>. This will compile all the source code,
run the tests and package the project as a *.jar file if all the tests pass.<br />
After this there are two options for running the application.
<ol>
    <li><b>As is: </b>From the same folder run: <em>./mvnw spring-boot:run</em></li>
    <li><b>As a container: </b>From the same folder run: 
    <em>docker build --platform linux/amd64 -t cake-manager .</em>(if you're doing this for the first time, and you 
    must have Docker installed). Then run <em>docker run -p 8282:8282 -t cake-manager</em></li>
</ol>
Both options will make the application available on http://localhost:8282

###Testing The Application:
You can test the endpoints using curl tool in the terminal or any GUI like Postman. There are four endpoints
available
<ul>
    <li>GET: <em>http://127.0.0.1:8282</em> Returns all the records in the table</li>
    <li>POST: <em>http://127.0.0.1:8282/cakes</em> Inserts new the records in the table. Sample payload: <pre>{
    "title": "Testing from postman",
    "description": "Description from postman",
    "imageUrl": "/image/postman.jpg"
    }</pre></li>
    <li>GET: <em>http://127.0.0.1:8282/cakes</em> Downloads all the records in the table as a json file</li>
    <li>GET: <em>http://127.0.0.1:8282/cake/{cake-d}</em> Returns a single record in the table</li>
</ul>

###Final Note:
I have configured a CI pipeline using GitHub for the project, and this will run every time new commits are pushed to the
remote repos. I did not implement OAuth2 authentication due to time constraints, jwt authentication would have been
faster to implement. I hope I’ve done enough to be considered for the role still.
