# rb0722
Tool Rental Application

This is a tool rental application that utilizes MySql to gather information from a database based on the tool code provided.

All of the queries to create the table and insert the data being used for this application is stored in the text file called CreateTableStatements_InsertData.

The reason I chose this route was for future changeability without the need to modify the code.  

Currently, the database contains four tools: 1. Stihl Chainsaws, 2. Werner Ladders, 3. DeWalt Jackhammers, 4. Ridgid Jackhammers. 

For example, if we suddenly began renting DeWalt Impact Wrenches or SunJoe Pressure Washers, we could add an entry to the Database without needing to change the logic in the code. 

This application currently utilizes 4 user inputs:
1. Prompt asking for a tool code. This is used to run an SQL query that gathers all other necessary information from the database. 
2. Prompt asking for the checkout date. This is used for future calculations in the code and allows us to setup the date information. 
3. Prompt asking for the amount of rental days. This is used for future calculations such as due date and charges applied.
4. Prompt asking for a discount amount. This allows the user to input a discount as a percent that will be applied to the final rental cost. 

After all user input is gathered, a series of calculations is done that generates a contract displaying all necessary information applied to the rental. 
