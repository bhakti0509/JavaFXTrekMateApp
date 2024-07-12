# TrekMate

TrekMate is an application designed to facilitate trekking enthusiasts in exploring and booking various treks. It integrates Firebase for backend services, providing secure authentication, real-time database, and cloud storage for trek images.

## Features

- **User Authentication**: Secure sign-up and login functionalities using Firebase Authentication.
- **Trek Listings**: Display a variety of treks with details such as location, duration, difficulty level, and cost.
- **Booking Management**: Allows users to book their preferred treks and manage their bookings.
- **Admin Dashboard**: Admin interface for managing trek details, including adding new treks and updating existing ones.
- **Firebase Integration**: Utilizes Firebase Firestore for storing trek data and Firebase Storage for storing trek images.

## Getting Started

To run TrekMate locally or in production, follow these steps:

### Prerequisites

1. **Java Development Kit (JDK)**: Ensure JDK 11 or higher is installed on your system.
2. **Firebase Project**: Create a Firebase project at [Firebase Console](https://console.firebase.google.com/).
3. **Firebase Configuration**: Obtain Firebase configuration details (`google-services.json` for Android, `GoogleService-Info.plist` for iOS, or Firebase Web SDK configuration).
4. **Environment Variables**: Set up the following environment variable in a `.env` file:
	```FIREBASE_BUCKET_NAME```