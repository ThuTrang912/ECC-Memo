# ECC-Memo
Memo Android application created with Kotlin

## Introduction
ECCmemo is a note-taking application developed as part of the final exam for the mobile application development course. The application allows users to create, edit, and manage notes with various useful features, helping users organize their tasks and remember information more easily.

<img src="https://github.com/user-attachments/assets/87dc58ad-3c46-4291-bcef-470bfaaf33c5" width="150" />

## Functional Requirements

### 1. Authentication (Auth)
- **1.1 Sign up**:
  - Use Firebase Authentication to allow users to register a new account.
  
  <img src="https://github.com/user-attachments/assets/2067c385-b1f6-48fc-9eba-ff31c63ff989" width="150" />

  - **Email/Password Authentication**:
    - Provide input fields for users including:
      - **Email**: User's email address.
      - **Password**: Password for the account.
    - Call the `createUserWithEmailAndPassword` function to create a new account.
    - Display error messages if the information is invalid (e.g., email already exists or password is too weak).
    - Upon successful registration, automatically log in the user and redirect them to the main screen of the application.
    
  <img src="https://github.com/user-attachments/assets/59e9453c-dab2-40a5-aec8-0e7e3a8cec6c" width="150" />

  - **Google Authentication**:
    - Use Firebase Authentication to integrate Google sign-in.
    - Provide a "Sign in with Google" button on the login screen.
    - When the user clicks this button, call the `signInWithPopup` or `signInWithRedirect` function from Firebase to open the Google sign-in window.
    - After the user completes the sign-in process, Firebase will return the user's information.
    - Store the user's information in Firestore to manage and display their notes.
    - Show success or error messages if the sign-in process fails.
    
  <img src="https://github.com/user-attachments/assets/836c392b-57f9-44cb-b285-ec609e12d65d" width="150" />
  <img src="https://github.com/user-attachments/assets/b03fead6-98ca-4374-8d88-befcff2632d4" width="150" />
  <img src="https://github.com/user-attachments/assets/1738bfa0-07fc-4a97-b3c1-f2b423c3e98f" width="150" />

- **1.2 Login Screen**:
  - Use Firebase Authentication to create a login screen.
  - Call the `signInWithEmailAndPassword` function to perform login.

  <img src="https://github.com/user-attachments/assets/d4fe19d4-2764-4a56-8922-701dd329d3e7" width="150" />
  <img src="https://github.com/user-attachments/assets/bd5fd222-51fb-46d9-8fd1-c1a70c7fe4f7" width="150" />

- **1.3 Check Login Status**:
  - Use `onAuthStateChanged` to check the login status. If the user is not logged in, redirect to the login screen.

  <img src="https://github.com/user-attachments/assets/f56229b6-6381-46f7-b740-99637ef8a2a9" width="150" />

### 2. Create Notes
- **2.1 Set Priority Level**:
  - Create a dropdown menu allowing users to select the priority level (high, medium, low).

- **2.2 Input Note Content**:
  - Use a text input field for users to enter the note content.

- **2.3 Save Note to Firebase**:
  - Use Firebase Firestore to save the note. Call the `addDoc` function to add the note to the collection.

  <img src="https://github.com/user-attachments/assets/526fe200-2868-4d40-965e-6e37bf5f1196" width="150" />

### 3. Display Notes
- **3.1 Show Notes**:
  - Retrieve the list of notes from Firestore using `getDocs` and display them in a list (ListView).

- **3.2 Display Content and Priority Level**:
  - Each item in the list will show the note content and its corresponding priority level.

  <img src="https://github.com/user-attachments/assets/62472669-faf8-4cc8-ad02-9e83061b721a" width="150" />

### 4. Edit Notes
- **4.1 Change Priority Level**:
  - Provide an edit button next to each note for users to change the priority level.
  - Use `updateDoc` to update the priority in Firestore.

- **4.2 Edit Note Content**:
  - When the edit button is pressed, open a dialog allowing users to edit the note content.
  - Save the changes by calling `updateDoc`.

  <img src="https://github.com/user-attachments/assets/cce6d494-faab-4ccc-8924-18d7fad36ca2" width="150" />
  <img src="https://github.com/user-attachments/assets/5eedd2f7-6b10-416c-9c07-12cba8b30355" width="150" />
  <img src="https://github.com/user-attachments/assets/3edf83a6-691d-4fc8-9e73-348609fde489" width="150" />

### 5. Logout
- **5.1 Create Options Menu**:
  - Use a UI library to create an options menu (overflow menu).

- **5.2 Implement Logout**:
  - Call the `signOut` function from Firebase Authentication when the user selects logout.

### 6. Delete Notes
- **6.1 Delete Note from Firebase**:
  - Provide a delete button next to each note. When pressed, call the `deleteDoc` function to remove the note from Firestore.

- **6.2 Update Interface**:
  - After deletion, refresh the notes list by calling the data retrieval function from Firestore.

  <img src="https://github.com/user-attachments/assets/34be6eb7-ec40-4743-aa4b-7af46428f373" width="150" />

### 7. User Switching
- **7.1 Login with Another Account**:
  - Provide a "Login" option on the main screen for users to log in with a different account.

- **7.2 Check Notes of Other Users**:
  - Use `where` in Firestore to retrieve only the notes of the current user.

- **7.3 Display Current User's Notes**:
  - When the user logs in, only display notes corresponding to their UID.

  <img src="https://github.com/user-attachments/assets/bdf71a4c-d6a2-4f91-9dbc-1867e694b68d" width="150" />
  <img src="https://github.com/user-attachments/assets/288fb067-cbf5-4082-b088-b0e95a904e64" width="150" />

### 8. User Interface (UI)
- **8.1 Change Application Icon**:
  - Create and add a new application icon to the project.

- **8.2 Enhance UI**:
  - Use modern and user-friendly UI components to improve user experience.

## Installation
Installation instructions for the application will be updated later. Users can download it from the app store or install it directly from the source code.

## Contact
If you have any questions or feedback, please contact the development team via email: [nguyenthithutrang0122@gmail.com].
