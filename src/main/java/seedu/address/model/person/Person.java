package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final LoanList loanList;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Loan loan) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.loanList = new LoanList();
        loanList.add(loan);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, LoanList loanList) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.loanList = loanList;
    }

    /**
     * Overloaded constructor for a Person with no Loans.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.loanList = new LoanList();
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable loan list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Loan> getLoans() {
        return loanList.getLoans();
    }

    /**
     * Returns the LoanList object representing a loan list.
     */
    public LoanList getLoanList() {
        return loanList;
    }

    /**
     * Adds a loan to the loan list.
     */
    public void addLoan(Loan loan) {
        loanList.add(loan);
    }

    /**
     * Adds the loans in a {@link LoanList} to this person's loan list.
     */
    public void addLoanList(LoanList loanList) {
        List<Loan> loansToAdd = loanList.getLoans();
        for (Loan loan : loansToAdd) {
            addLoan(loan);
        }
    }

    /**
     * Removes a loan from the loan list.
     */
    public void removeLoan(Loan loan) {
        loanList.remove(loan);
    }

    public void removeLoan(int zeroBasedIndex) {
        loanList.remove(zeroBasedIndex);
    }

    /**
     * Pays a specified amount to a loan.
     */
    public void payLoan(int index, float amount) throws IllegalValueException {
        loanList.payLoan(index, amount);
    }
    /**
     * Returns the number of loans the loan list contains.
     */
    public int getLoanCount() {
        return getLoans().size();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("loans", loanList)
                .toString();
    }
}
