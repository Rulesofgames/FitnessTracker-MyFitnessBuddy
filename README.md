# FitnessTracker-MyFitnessBuddy
FitnessTracker-MyFitnessBuddy

**Demo Link:** https://www.youtube.com/watch?v=TILvTbunSIE

**Overview**
This project is a full-stack web application that leverages Spring Tool Suite (STS) for backend development and a combination of frontend technologies, including JavaScript, HTML, CSS, and jQuery. The backend, powered by Java and the Spring Framework, serves as the middleware, while MySQL is used as the database layer.

**Technologies Used**
Frontend: JavaScript, HTML, CSS, jQuery, AJAX
Backend: Spring Tool Suite (STS), Java, Spring Framework
Database: MySQL

**Features**

User Registration - 
1.New users can create an account by providing their email address and password.
2. The registration form ensures that users enter valid email addresses and secure passwords.

Email Verification - 
1. Upon successful registration, a welcome email is automatically sent to the user's provided email address.
2.This email serves as a confirmation of the registration process and welcomes the user to the platform.

Password Criteria Validation - 
1. During registration, the application checks whether the user's chosen password meets specified criteria, such as length and complexity.
2. jQuery AJAX is employed to validate the password on the client side, ensuring immediate feedback to the user.

Email Uniqueness Check - 
1. To prevent multiple registrations with the same email address, the system uses jQuery AJAX to check if the entered email address is already associated with an existing account.
2. Users are notified in real-time if they attempt to register with an email address that is already in use.

Workout Tracking - 
1. After logging in, users can add workout details with the following information: Date,Start time,End time,Body weight,Duration,Notes.
2. Users have the flexibility to edit previously logged workouts.
3. Users can delete individual workout entries if they wish to remove them from their workout history.

Exercise Set Management - 
1.Users can add exercise sets to each workout, specifying the following details for each set: Exercise name, Weight(if applicable), Repetitions (Reps),Duration in minutes, Distance (if applicable), Calories burned (if applicable), Notes.
2. Users have the flexibility to edit individual exercise sets within a workout.
3. Users can delete specific exercise sets from their workout entries.

Dashboard View - 
1. The dashboard provides a comprehensive view of monthly workout details, organized by month.
2. Within each month, workout data is further broken down into day-wise summaries.
3. For each day, workout details are categorized into Morning, Mid-day, Afternoon, Evening, and Night workouts.
4. Users can easily see the number of workouts performed in each month.
5. The dashboard also displays the total duration of workouts for each month.
6. On hovering over each exercise entry, a hover window displays detailed information for each exercise set. This includes exercise name, weight, reps, duration, distance, calories burned, and any notes associated with the set. This feature provides users with a quick and detailed view of their workout progress without navigating away from the dashboard.

Statistics - 
The Statistics tab allows users to generate graphs and visualizations based on the following parameters:
1. Exercise Category: Users can select from categories such as Overall statistics,Cardio, Abs , Shoulder, Legs etc.
2. Exercise Sub-Category: Further refinement of exercises within the selected category, e.g., Threadmill, Rowing Machine, Running, Cycling.
3. Metric: Users can choose the metric they want to visualize, such as Calories Burned, Distance Covered, or Workout Duration in Minutes.
4. Timeframe: Users can select the desired timeframe for the statistics, including options like the Last 7 Days, Last 1 Month, Last 3 Months, or Last 6 Months.

Profile Editing - 
1.Users can edit their profiles, making changes to the following information: Email, Password, First Name, Last Name, Date of Birth (DOB), Address, Pincode, Country ,Gender.
2. User will receive confirmation message after successfully updating your profile information 

Log Out - 
1. The "Log Out" feature allows users to securely log out of their accounts, terminating their current session.
2. After logging out, users are redirected to the sign-in page.

**Screenshots **

