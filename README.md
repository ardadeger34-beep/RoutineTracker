                   Project Report: Routine Tracker Application
1. Project Title
Routine Tracker
2. Student Name(s) and ID(s)
• Dursun Ali Akturan
• Mehmet Emre İlter 
• Arda Değer 
3. Selected Project Option
Option 1: Accessible Daily Reminder App (Mobile Application Development for Disadvantaged Individuals)
4. Purpose of the App
The main purpose of this app is to help autistic individuals manage their daily tasks. Autistic people often rely on strict routines, and forgetting a task or facing a sudden change can cause anxiety. This app provides a simple, distraction-free environment to schedule tasks and gives reliable notifications exactly on time to help them gain independence and reduce daily stress.
5. Main Features
•    Routine Management: Users can easily add, edit, or delete daily routines and assign importance levels (High, Medium, Low).
•    Reliable Notifications: The app sends accurate background alerts exactly at the chosen time, even if the app is closed.
•    Accessible UI: A clean, dark-mode compatible interface that is easy to read and doesn't overwhelm the user with unnecessary buttons.
•    Settings Customization: Users have full control to toggle notifications, vibration, and sound on or off based on their sensory preferences.
•    Motivation Screen: A dedicated screen that shows motivational quotes to encourage users throughout the day.
6. Technical Structure
The application is developed natively for Android using Java, following Object-Oriented Programming (OOP) principles. The codebase is organized into modular layers to maintain scalability and reliable background execution:
•    Data Layer: We used SharedPreferences with a RoutineRepository class to save user routines locally in JSON format.
•    UI Layer: Built with XML. We used explicit Intents to navigate between MainActivity, AddEditActivity, SettingsActivity, and MotivationActivity.
•    Background Services: We utilized Android's AlarmManager with the setExact() method. We created an AlarmReceiver and a BootReceiver (registered in the AndroidManifest.xml) so alarms persist after device reboots. A NotificationHelper class handles the system notification channels.
7. Challenges and Solutions
•    Challenge: Entering the date and time manually with a keyboard caused formatting errors and crashed the background alarm logic.
Solution: We disabled the keyboard input (android:focusable="false") and implemented Android's native DatePickerDialog and TimePickerDialog. This forces a clean format and provides a better user experience.
•    Challenge: Alarms were not triggering on newer Android versions.
Solution: We added SCHEDULE_EXACT_ALARM permissions to the Manifest and wrote a runtime check (canScheduleExactAlarms()) in Java to comply with Android 12+ security rules.
•    Challenge: UI elements were hard to read in Dark Mode and overlapped with the phone's top status bar.
Solution: We used dynamic system colors (?android:attr/colorBackground and textColorPrimary) and added android:fitsSystemWindows="true" to fix the screen layout.
8. Conclusion
Through this project, we successfully built an accessible reminder app tailored for autistic individuals. We learned how to handle complex Android background services like AlarmManager and BroadcastReceiver, manage local data with SharedPreferences, and design a bug-free, user-friendly interface. The final app fulfills all the technical and thematic requirements of the project.

