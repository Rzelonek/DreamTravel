# DreamTravel
[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

> [!IMPORTANT]  
> This is project for studies 

<p align="center">
  <img src="https://github.com/user-attachments/assets/6f675a36-d98a-4b29-9730-ad994e77366a" alt="Sublime's custom image"/>

</p>


#  DreamTravel

DreamTravel is a comprehensive travel booking platform built using Java Spring Boot, offering a rich set of features designed for both users and administrators. The application caters to travelers by allowing them to browse, select, and purchase trips, while providing an administrative panel for managing the platform. 
![Zrzut ekranu 2024-10-27 204434](https://github.com/user-attachments/assets/501e5aa2-61a1-44ba-be63-09c9a6221d4e)


Below are the key features and functionalities included in DreamTravel:
1. User Authentication
Login & Registration: Users can register accounts and log in to access the platform’s features. The authentication mechanism is built using Spring Security, ensuring a secure login process with encrypted passwords.
Roles: The application supports two types of roles:
USER: For regular users looking to browse and book trips.
ADMIN: For administrators responsible for managing the platform's content.
Session Management: User sessions are maintained throughout their interaction with the site, allowing them to access protected features like cart management and balance updates.

2. Shopping Cart
Add Trips to Cart: Users can browse through available trips and add their selections to the shopping cart. The cart monitors the availability of trips in real time.
Quantity Tracking: The cart checks for trip availability and adjusts accordingly, preventing users from booking trips that are sold out.
Cart Checkout: Users can review their selected trips and proceed to payment.

3. Balance Management
Balance View & Update: Users can view and manage their current balance through a balance page.
Card Validation: When adding balance through a credit card, the platform validates the card details, including card number, expiration date, and security code. The balance is updated only after successful validation.
Secure Payments: Payments are securely processed, and card information is handled with the highest security standards.

4. Admin Panel
Trip Management: The admin panel provides administrators with the ability to:
Add New Trips: Admins can create new trips, including details such as destination, description, price, and availability.
Edit Existing Trips: Administrators can update trip information, such as availability, pricing, and trip photos.
Image Upload: Photos for trips can be uploaded via the admin panel using the MultipartFile feature for enhanced trip visibility.

5. Order History
View Orders: Users can view their past and current orders, which display the trips they have booked, including trip details and statuses.
Order Details: The orders section includes detailed information about each booking, such as trip date, number of travelers, and total price.


## Technology Stack 🛠
**Backend:** Java Spring Boot for business logic and database management.

**Frontend:** Thymeleaf templates integrated with Spring MVC for rendering dynamic content.

**Database:** h2

**Security:** Spring Security for handling authentication and role-based access control.

**Validation:** Built-in form validations and custom validation annotations for card processing and user input validation.


## FAQ 💬


#### How to log in as Admin?
```bash
 login: admin
```
```bash
password: admin
```


#### How to log in as Guest?

```bash
 login: guest
```
```bash
password: guest
```

> [!CAUTION]
> The upload limit is 5 MB for files such as trip images.





 ## Roadmap 🧠

DreamTravel is a constantly evolving platform designed to meet user needs for flexible trip booking, while providing a streamlined experience for both travelers and administrators. With upcoming features like 

 - trip date selectors,
 - chat in active orders,
 - and improved UI/UX,


DreamTravel aims to be the go-to open srouce platform for all travel agencies.





## Screenshots

 ![Zrzut ekranu 2024-10-27 204504](https://github.com/user-attachments/assets/d6bf5252-d8d6-4f77-b1d9-12e720afa627)

 ![Zrzut ekranu 2024-10-27 205145](https://github.com/user-attachments/assets/15b2a440-9725-4ba9-9d78-ca1cbca8d4b9)

![Zrzut ekranu 2024-10-27 205023](https://github.com/user-attachments/assets/06ca28be-a095-4807-a946-b077b2f038da)

![Zrzut ekranu 2024-10-27 204733](https://github.com/user-attachments/assets/29ee5fdc-f7a0-424c-bfb2-87fb0a1a0557)


