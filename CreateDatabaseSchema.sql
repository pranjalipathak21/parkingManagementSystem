create database parkingmanagementsystem;
show databases;
use parkingmanagementsystem;

CREATE TABLE Vehicle_Category (
  Category_id INT AUTO_INCREMENT PRIMARY KEY,
  Abbreviation VARCHAR(255) UNIQUE NOT NULL,
  Category VARCHAR(255) NOT NULL
);

desc Vehicle_Category;

CREATE TABLE Parking_Lot (
  Lot_id INT AUTO_INCREMENT PRIMARY KEY,
  Area VARCHAR(255) NOT NULL,
  City VARCHAR(255) NOT NULL,
  postal_code INT NOT NULL DEFAULT 0,
  Floors INT NOT NULL,
  Slots INT NOT NULL
);

desc Parking_Lot;

CREATE TABLE Lot_Charge_Sheet (
  sheet_id int,
  Lot_id INT,
  Category_id INT,
  rate_per_hour INT NOT NULL DEFAULT 0,
  FOREIGN KEY (Lot_id) REFERENCES Parking_Lot(Lot_id),
  FOREIGN KEY (Category_id) REFERENCES Vehicle_Category(Category_id)
);

desc Lot_Charge_Sheet;

CREATE TABLE Role (
  Role_id INT AUTO_INCREMENT PRIMARY KEY,
  Role_Name VARCHAR(255) NOT NULL
);

desc Role;

CREATE TABLE Employee (
  Employee_id INT AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(255) NOT NULL,
  AadharNo VARCHAR(255) NOT NULL DEFAULT 'NA',
  Lot_id INT,
  Role_id INT,
  FOREIGN KEY (Lot_id) REFERENCES Parking_Lot(Lot_id),
  FOREIGN KEY (Role_id) REFERENCES Role(Role_id)
);

desc Employee;

CREATE TABLE Ticket_Registry (
  Ticket_id INT AUTO_INCREMENT PRIMARY KEY,
  Vehicle_No VARCHAR(255) NOT NULL,
  Phone_No VARCHAR(255),
  Entry_Time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  Exit_Time TIMESTAMP DEFAULT NULL,
  Fare INT NOT NULL DEFAULT 0,
  Payment_Status VARCHAR(255) NOT NULL DEFAULT "PENDING",
  Category_id INT,
  Employee_id INT,
  Lot_id INT,
  FOREIGN KEY (Category_id) REFERENCES Vehicle_Category(Category_id),
  FOREIGN KEY (Employee_id) REFERENCES Employee(Employee_id),
  FOREIGN KEY (Lot_id) REFERENCES Parking_Lot(Lot_id),
  CONSTRAINT CheckAllowedValues CHECK (Payment_Status IN ('PENDING', 'PAID'))
);

desc Ticket_Registry;

INSERT INTO Vehicle_Category (Abbreviation, Category) VALUES
  ('CAR', 'Compact Car'),
  ('SUV', 'Sports Utility Vehicle'),
  ('TRK', 'Truck'),
  ('MTR', 'Motorcycle'),
  ('VAN', 'Van');
  
  select * from Vehicle_Category;
  
INSERT INTO Parking_Lot (Area, City, postal_code, Floors, Slots) VALUES
  ('Central Business District', 'Mumbai', 400001, 5, 50),
  ('Technology Park', 'Bengaluru', 560001, 7, 30),
  ('Shopping Complex', 'Delhi', 110001, 3, 10),
  ('Airport Terminal', 'Chennai', 600001, 4, 50),
  ('Industrial Area', 'Hyderabad', 500001, 6, 27),
  ('Educational Campus', 'Kolkata', 700001, 8, 30),
  ('Residential Area', 'Pune', 411001, 2, 110),
  ('Tourist Hub', 'Jaipur', 302001, 5, 50),
  ('Medical District', 'Ahmedabad', 380001, 4, 80),
  ('Cultural Center', 'Lucknow', 226001, 3, 30);
  
  select * from Parking_Lot;
  
  INSERT INTO Lot_Charge_Sheet (sheet_id, Lot_id, Category_id, rate_per_hour) VALUES
  (1, 1, 1, 20),
  (2, 1, 2, 20),
  (3, 1, 3, 30),
  (4, 2, 1, 30),
  (5, 2, 2, 30),
  (6, 2, 3, 35),
  (7, 3, 4, 10),
  (8, 3, 2, 30),
  (9, 3, 3, 25),
  (10, 4, 1, 30);
  
  select * from Lot_Charge_Sheet;
  
  INSERT INTO Role (Role_Name) VALUES
  ('Admin'),
  ('Parking Authority');
  
  select * from Role;
  
  INSERT INTO Employee (Name, AadharNo, Lot_id, Role_id) VALUES
  ('Rajesh Kumar', '1234-5678-9012', 1, 1),
  ('Priya Sharma', '5678-1234-9012', 1, 2),
  ('Amit Singh', '9012-5678-1234', 2, 2),
  ('Deepika Patel', '2345-6789-0123', 2, 2),
  ('Sandeep Verma', '6789-0123-4567', 3, 2),
  ('Neha Gupta', '0123-4567-8901', 3, 1),
  ('Vikram Malhotra', '3456-7890-1234', 4, 1),
  ('Sakshi Choudhary', '8901-2345-6789', 4, 2),
  ('Rahul Khanna', '4567-8901-2345', 5, 2),
  ('Anjali Das', '7890-1234-5678', 5, 2);
  
  select * from Employee;
  
  INSERT INTO Ticket_Registry (Vehicle_No, Phone_No, Exit_Time, Fare, Payment_Status, Category_id, Employee_id, Lot_id) VALUES
  ('MH01AB1234', '9876543210', '2023-09-16 16:00:00', 250, 'Paid', 1, 2, 1),
  ('DL02CD5678', null, null, 0, 'Pending', 2, 3, 2),
  ('KA03EF9012', null, null, 0, 'Pending', 3, 5, 3),
  ('TN04GH3456', null, '2023-09-16 19:15:00', 350, 'Paid', 1, 8, 4),
  ('UP06KL1234', '9998887777', null, 0, 'Pending', 3, 7, 4);
  
    INSERT INTO Ticket_Registry (Vehicle_No, Phone_No, Entry_Time, Category_id, Employee_id, Lot_id) VALUES
  ('MH01AB1256', '9876543210', '2023-10-27 16:00:00', 'Pending', 1, 2, 1);
  
  select * from Ticket_Registry;
  
  
  
  
DELIMITER //
CREATE PROCEDURE ComputeTicketFare(
    IN input_ticket_id INT,
	OUT fare_amount DECIMAL(10, 2)
)
BEGIN
    UPDATE Ticket_Registry
    SET Exit_Time = CURRENT_TIMESTAMP
    WHERE Ticket_Id = input_ticket_id;
	
	SELECT Fare into fare_amount from Ticket_Registry WHERE Ticket_Id = input_ticket_id;
END //

DELIMITER ;




DELIMITER //

CREATE TRIGGER calculate_fare_trigger
BEFORE UPDATE ON Ticket_Registry
FOR EACH ROW
BEGIN
    DECLARE fare_amount INT;
	DECLARE MESSAGE_TEXT VARCHAR(255);
    
    SET MESSAGE_TEXT = 'Executing trigger calculate_fare_trigger';
    
    -- Calculate fare based on Vehicle_Category and Parking_Lot
    SELECT lcs.rate_per_hour
    FROM Lot_Charge_Sheet lcs
	WHERE lcs.Lot_id = NEW.Lot_id and lcs.Category_id=NEW.Category_id
    INTO fare_amount;

    SET MESSAGE_TEXT = CONCAT( 'rate_per_hour is ', fare_amount) ;
    
    IF fare_amount IS NOT NULL THEN
		IF NEW.Fare = 0 and NEW.Payment_Status = 'PENDING' then
			SET NEW.Fare = TIMESTAMPDIFF(HOUR, NEW.Entry_Time, COALESCE(NEW.Exit_Time, CURRENT_TIMESTAMP())) * fare_amount;
            
            SET MESSAGE_TEXT = CONCAT( 'Computed fare is ', NEW.Fare) ;
        END IF;
    END IF;
END //

DELIMITER ;