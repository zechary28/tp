---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# 🦈 The Sharkster User Guide

The Sharkster is a **desktop application for managing loan records**, optimized for use via a **Command Line Interface (CLI)** while still offering a **Graphical User Interface (GUI)**. If you can type fast, The Sharkster helps you manage loans, repayments, and outstanding debts **more efficiently** than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar sharkster.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `loan 1 s 1000 10 2027-10-10` : Creates a new simple interest loan for a selected borrower.

    * `sort` : Sort borrowers by name and order asc (default).

    * `list` : Lists all recorded borrowers and their loans.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a borrower named John Doe to Sharkvies.

    * `clear` : Deletes all borrower records.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Adding a Loan : `loan`

Adds a loan to a contact in the address book. Loans can be either **Simple Interest Loans** or **Compound Interest Loans**.

Format: `loan INDEX TYPE AMOUNT INTEREST_RATE DUE_DATE​`
- `INDEX` refers to the index number of the contact as displayed in the contact list.
- `TYPE` is either `s` (Simple Interest Loan) or `c` (Compound Interest Loan).
- `AMOUNT` is the principal loan amount (e.g., `100.00`).
- `INTEREST_RATE` is the percentage interest rate (e.g., `5.5` for `5.5%`).
- `DUE_DATE` is the loan's due date in `YYYY-MM-DD` format.


Examples:
* `loan 1 s 100.00 5.5 2025-12-31`
* `loam 2 c 500.00 7.0 2026-06-15`

---

### Sorting the borrowers: `sort`

Sorts the borrowers by parameter and order.

**Fromat:** `sort s/PARAMETER o/ORDER`
- `PARAMETER` refers to which parameter to sort by `AMOUNT` (Total amount of loans owed for each borrower), `OVERDUE` (Borrower with the most overdue loan), `NAME` (Name of borrower).
- `AMOUNT` refer to order which to sort by. (`ASC` or `DESC`).

**Example:** `sort s/AMOUNT o/ASC`

---

### Recording a Payment: `pay`

Marks a payment made by the loanee.

**Fromat:** `pay INDEX AMOUNT DUE`
- `INDEX` refers to the index number of the loanee in the contact list.
- `AMOUNT` is the amount paid.
- `DATE` is the payment date in `YYYY-MM-DD` format.

**Example:** `pay 1 50.00 2025-06-01`

---

### Deleting a Loanee: `delete`

Deletes all loan details associated with a loanee.

**Format:** `delete INDEX_B INDEX_L`
- `INDEX_B` refers to the index number of the loanee in the contact list.
- `INDEX_L` refers to the index number of the loanee's loan.

**Example:** `delete 2`

---

### Listing all Loanees: `list`

Displays a list of all loanees in the address book.

**Format:** `list`

---

### Editing Loanee Details: `edit`

Edits the details of a loanee.

**Format:** `edit INDEX FIELD NEW_VALUE`
- `INDEX` refers to the index number of the loanee in the contact list.
- `FIELD` specifies which detail to edit (`name`, `phone`, `email`, `address`).
- `NEW_VALUE` is the updated information.

**Example:** `edit 1 phone 91234567`

---

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX_B INDEX_L`<br> e.g., `delete 3 1`
**Sort**   | `sort [s/PARAMETER] [o/ORDER]`<br> e.g., `sort s/AMOUNT o/ASC`
**Pay**    | `pay INDEX AMOUNT`<br> e.g., `pay 1 1000`
**Filter** | `filter to be done`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
