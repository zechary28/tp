package seedu.address.model.person;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Tests that a {@code Loan}'s parameter matches any of the conditions given.
 */
public class LoanPredicate implements Predicate<Loan> {
    private final LoanParameter parameter;
    private final Optional<Float> value;
    private final Optional<LocalDate> date;
    private final Optional<Character> operator;

    public enum LoanParameter {
        AMOUNT, DUE_DATE, LOAN_TYPE, IS_PAID, INVALID;

        public static LoanParameter fromString(String paramType) {
            switch (paramType.toLowerCase()) {
            case "amount":
                return AMOUNT;
            case "duedate":
                return DUE_DATE;
            case "loantype":
                return LOAN_TYPE;
            case "ispaid":
                return IS_PAID;
            default:
                return INVALID;
            }
        }
    }

    public LoanPredicate(String args) {
        String[] tokens = args.split("\\s+");
        if (tokens.length == 0) {
            this.parameter = LoanParameter.INVALID;
            this.value = Optional.empty();
            this.date = Optional.empty();
            this.operator = Optional.empty();
        } else {
            this.parameter = LoanParameter.fromString(tokens[0]);
            if (this.parameter == LoanParameter.AMOUNT) {
                String op = tokens[1];
                this.operator = (op.equals("<") || op.equals(">"))
                        ? Optional.of(op.charAt(0))
                        : Optional.empty();
                this.value = Optional.of(Float.parseFloat(tokens[2]));
                this.date = Optional.empty();
            } else if (this.parameter == LoanParameter.DUE_DATE) {
                String op = tokens[1];
                this.operator = (op.equals("<") || op.equals(">"))
                        ? Optional.of(op.charAt(0))
                        : Optional.empty();
                this.value = Optional.empty();
                this.date = Optional.of(LocalDate.parse(tokens[2]));
            } else if (this.parameter == LoanParameter.LOAN_TYPE) {
                String op = tokens[1];
                this.operator = (op.equals("s") || op.equals("c"))
                        ? Optional.of(op.charAt(0))
                        : Optional.empty();
                this.value = Optional.empty();
                this.date = Optional.empty();
            } else if (this.parameter == LoanParameter.IS_PAID) {
                String op = tokens[1];
                this.operator = (op.equals("y") || op.equals("n"))
                        ? Optional.of(op.charAt(0))
                        : Optional.empty();
                this.value = Optional.empty();
                this.date = Optional.empty();
            } else {
                this.operator = Optional.empty();
                this.value = Optional.empty();
                this.date = Optional.empty();
            }
        }
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
