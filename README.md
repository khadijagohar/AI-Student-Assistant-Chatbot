<h1 align="center">🎓 AI Student Assistant Chatbot</h1>

<p align="center">
An AI-powered Java desktop application designed to help university students manage academic tasks and receive intelligent study assistance through a chatbot powered by the Gemini API.
</p>

<hr>

<h2>📌 Project Overview</h2>
<p>
The AI Student Assistant Chatbot is a Java-based desktop application that integrates user authentication,
task management, and an AI-powered chatbot into a single platform.  
It aims to improve student productivity and enhance learning by providing both task organization
and instant study support.
</p>

<hr>

<h2>🚀 Features</h2>
<ul>
  <li>🔐 <b>Login & Signup System</b><br>Secure authentication for students using a database.</li>
  <br>
  <li>📋 <b>Task & Reminder Management</b><br>Displays academic tasks and reminders in a table fetched directly from the database.</li>
  <br>
  <li>🤖 <b>AI-Powered Chatbot (Gemini API)</b><br>Provides general study help and answers student queries using artificial intelligence.</li>
  <br>
  <li>🗄 <b>Database Integration (MySQL)</b><br>Stores user credentials and task information persistently.</li>
  <br>
  <li>🖥 <b>Graphical User Interface (Java Swing)</b><br>Simple, interactive, and user-friendly desktop interface.</li>
</ul>

<hr>

<h2>🛠 Tech Stack</h2>
<ul>
  <li><b>Programming Language:</b> Java</li>
  <li><b>GUI:</b> Java Swing</li>
  <li><b>Database:</b> MySQL</li>
  <li><b>IDE:</b> VS Code</li>
  <li><b>AI API:</b> Google Gemini API</li>
  <li><b>Platform:</b> Desktop Application</li>
</ul>

<hr>

<h2>📂 Project Modules</h2>
<ul>
  <li><b>Authentication Module</b> – Handles login and signup functionality.</li>
  <li><b>Task Management Module</b> – Fetches and displays tasks from the database.</li>
  <li><b>Chatbot Module</b> – AI-based study assistant using Gemini API.</li>
  <li><b>Database Module</b> – Manages MySQL connectivity and data storage.</li>
</ul>

<hr>

<h2>⚙ Installation & Setup</h2>

<ol>
  <li>Clone the repository:
    <pre>git clone https://github.com/your-username/ai-student-chatbot.git</pre>
  </li>

  <li>Open the project in <b>VS Code</b>.</li>

  <li>Configure MySQL Database:
    <ul>
      <li>Create a database (e.g. <code>student_chatbot</code>).</li>
      <li>Import the provided SQL file (if available).</li>
      <li>Update database credentials in the code:</li>
    </ul>
<pre>
String url = "jdbc:mysql://localhost:3306/student_chatbot";
String user = "root";
String password = "your_password";
</pre>
  </li>

  <li>Add Gemini API Key:
    <ul>
      <li>Insert your API key in the chatbot configuration file.</li>
    </ul>
  </li>

  <li>Run the main Java file:
<pre>
javac Main.java
java Main
</pre>
  </li>
</ol>

<hr>

<h2>🧠 How It Works</h2>
<ol>
  <li>User logs in or signs up.</li>
  <li>The task table fetches academic tasks from MySQL and displays them.</li>
  <li>User interacts with the chatbot for study-related help.</li>
  <li>The chatbot sends queries to the Gemini API and displays intelligent responses.</li>
</ol>

<hr>

<h2>👨‍🎓 Target Users</h2>
<ul>
  <li>University students</li>
  <li>Computer Science students</li>
  <li>Students needing academic assistance</li>
</ul>

<hr>

<h2>🔮 Future Enhancements</h2>
<ul>
  <li>⏰ Real-time reminder notifications</li>
  <li>🎤 Voice-based chatbot interaction</li>
  <li>📱 Mobile application version</li>
  <li>📊 Advanced task analytics</li>
  <li>📚 Subject-specific tutoring support</li>
</ul>

<hr>

<h2>📜 License</h2>
<p>
This project is developed for educational purposes only.
</p>

<hr>

<h2>💡 Author</h2>
<p>
<b>Khadija Gohar</b><br>
BS Computer Science Student
</p>

