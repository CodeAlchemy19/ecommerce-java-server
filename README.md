☕ Backend – Spring Boot + MySQL + JWT
The backend of VKStore is built with Spring Boot 3+, MySQL, and secured using JWT (JSON Web Tokens). It provides REST APIs for authentication, product management, cart, wishlist, and more.

🛡️ Features
✅ Secure JWT Authentication (Login / Signup / Token Validation)

✅ CRUD APIs for products, cart, and wishlist

✅ Integration-ready with React frontend

✅ MySQL for persistent data storage

✅ CORS configured for local/frontend access

🚀 How to Run (Backend)
1. Clone the Repository
bash
Copy
Edit
git clone https://github.com/trivikram-coder/ecommerce-java-server.git
cd ecommerce-java-server
2. Configure Database
Make sure MySQL is installed and running.

Create a database named (e.g., vkstore).

Update your application.properties or application.yml with your DB credentials:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/vkstore
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
3. Run the Application
bash
Copy
Edit
mvn spring-boot:run
✅ Backend will run at: http://localhost:8080

🔐 JWT Auth Endpoints
Method	Endpoint	Description
POST	/auth/signup	Register new user
POST	/auth/login	Authenticate & get JWT
GET	/auth/details	Get user from token
GET	/products/get	Public product list
POST	/cart/add	Add to cart (auth)

✅ All secure routes require Authorization header:
Authorization: Bearer <your_token>

