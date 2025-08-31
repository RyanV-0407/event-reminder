📅 Event Manager System

A simple Spring Boot + Vaadin application to create, manage, and track events with ease.

🚀 Features

 • Create, update, and delete events
 
 • Set reminders and notifications
 
 • User-friendly Vaadin UI

🛠️ Tech Stack

 • Java + Spring Boot
 
 • Vaadin
 
 • Maven

⚡ Run Locally
  cd event-reminder
  
  mvn spring-boot:run
  
  Open in browser: 👉 http://localhost:8080

📦 Build for Production
To create a production build, call `mvn clean package -Pproduction`.

This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/myapp-1.0-SNAPSHOT.jar` (NOTE, replace 
`myapp-1.0-SNAPSHOT.jar` with the name of your jar).

📂 Project structure

- `MainView.java` in `src/main/java` is an example Vaadin view.
- `src/main/resources` contains configuration files and static resources
- The `frontend` directory in the root folder is where client-side 
  dependencies and resource files should be placed.

event-manager-system/

│── src/main/java/       # Java source code

│   ├── com.example.*    # Application packages

│   └── MainView.java    # Sample Vaadin view

│

│── src/main/resources/  # Config files & static resources

│── frontend/            # Client-side dependencies

│── pom.xml              # Maven project file



📖Useful links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorials at [vaadin.com/tutorials](https://vaadin.com/tutorials).
- Watch training videos and get certified at [vaadin.com/learn/training]( https://vaadin.com/learn/training).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/components](https://vaadin.com/components).
- Find a collection of solutions to common use cases in [Vaadin Cookbook](https://cookbook.vaadin.com/).
- Find Add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/).

  🤝 Contributing

Pull requests are welcome! If you’d like to contribute:

 • Fork the repo
 
 • Create a new branch (feature/new-feature)
 
 • Commit changes
 
 • Submit a pull request
 
