package seedu.address.model.person;

import seedu.address.logic.parser.ParserUtil;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Tests that a {@code Loan}'s parameter matches any of the conditions given.
 */
public class LoanPredicate implements Predicate<Loan> {
    private final ParserUtil.LoanParameter parameter;
    private final Optional<Integer> index;
    private final Optional<Float> value;
    private final Optional<LocalDate> date;
    private final Optional<Character> operator;

    public LoanPredicate(ParserUtil.LoanParameter parameter,
                         Optional<Integer> index,
                         Optional<Float> value,
                         Optional<LocalDate> date,
                         Optional<Character> operator) {
        this.parameter = parameter;
        this.index = index;
        this.value = value;
        this.date = date;
        this.operator = operator;
    }

    @Override
    public boolean test(Loan loan) {
        switch (this.parameter) {
        case AMOUNT: {
            assert operator.isPresent() && (operator.get() == '<' || operator.get() == '>') : "Operator for amount must be < or >";
            if (operator.get() == '<') {
                return loan.getRemainingOwed() < this.value.get();
            } else {
                return loan.getRemainingOwed() >= this.value.get();
            }
        }
        case DUE_DATE: {
            assert operator.isPresent() && (operator.get() == '<' || operator.get() == '>') : "Operator for dueDate must be < or >";
            if (operator.get() == '<') {
                return loan.getDueDate().isBefore(this.date.get());
            } else {
                return loan.getDueDate().isAfter(this.date.get()) || loan.getDueDate().isEqual(this.date.get()) ;
            }
        }
        case LOAN_TYPE: {
            assert operator.isPresent() && (operator.get() == 's' || operator.get() == 'c') : "Operator for loanType must be s or c";
            if (operator.get() == 's') {
                return loan instanceof SimpleInterestLoan;
            } else {
                return loan instanceof CompoundInterestLoan;
            }
        }
        case IS_PAID: {
            assert operator.isPresent() && (operator.get() == 'y' || operator.get() == 'n') : "Operator for isPaid must be y or n";
            char op = this.operator.get();
            return op == 'y' ? loan.isPaid() : !loan.isPaid();
        }
        case INVALID: {
            // todo throw exception
            return true;
        }
        default: {
            return true;
        }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LoanPredicate)) {
            return false;
        }

        LoanPredicate otherLoanPredicate = (LoanPredicate) other;
        return parameter == otherLoanPredicate.parameter;
    }

    @Override
    public String toString() {
        return String.format("LoanPredicate{parameter=%s, value=%s, date=%s, operator=%s}",
                parameter, value.orElse(null), date.orElse(null), operator.orElse(null));
    }
}
