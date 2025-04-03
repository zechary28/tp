package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.LoanPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String loanPredicate} into a {@code LoanPredicate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code loanPredicate} is invalid.
     */
    public static LoanPredicate parseLoanPredicate(String args) throws ParseException {
        String[] tokens = requireNonNull(args).trim().split("\\s+");
        LoanPredicate.LoanParameter parameter;
        Optional<Float> value;
        Optional<LocalDate> date;
        Optional<Character> operator;
        if (tokens.length == 0) {
            throw new ParseException("No tokens found");
        } else {
            parameter = LoanPredicate.LoanParameter.fromString(tokens[0]);
            if (parameter == LoanPredicate.LoanParameter.AMOUNT) {
                if (tokens.length < 3) {
                    throw new ParseException("Insufficient Arguments");
                }
                char op = tokens[1].charAt(0);
                if (!(op == '<' || op == '>')) {
                    throw new ParseException("Amount operator must be < or >");
                }
                operator = Optional.of(op);
                value = Optional.of(Float.parseFloat(tokens[2]));
                date = Optional.empty();
            } else if (parameter == LoanPredicate.LoanParameter.DUE_DATE) {
                if (tokens.length < 3) {
                    throw new ParseException("Insufficient Arguments");
                }
                char op = tokens[1].charAt(0);
                if (!(op == '<' || op == '>')) {
                    throw new ParseException("DueDate operator must be < or >");
                }
                operator = Optional.of(op);
                value = Optional.empty();
                date = Optional.of(LocalDate.parse(tokens[2]));
            } else if (parameter == LoanPredicate.LoanParameter.LOAN_TYPE) {
                if (tokens.length < 2) {
                    throw new ParseException("Insufficient Arguments");
                }
                char op = tokens[1].charAt(0);
                if (!(op == 's' || op == 'c')) {
                    throw new ParseException("LoanType operator must be s or c");
                }
                operator = Optional.of(op);
                value = Optional.empty();
                date = Optional.empty();
            } else if (parameter == LoanPredicate.LoanParameter.IS_PAID) {
                if (tokens.length < 2) {
                    throw new ParseException("Insufficient Arguments");
                }
                char op = tokens[1].charAt(0);
                if (!(op == 'y' || op == 'n')) {
                    throw new ParseException("Amount operator must be y or n");
                }
                operator = Optional.of(op);
                value = Optional.empty();
                date = Optional.empty();
            } else {
                operator = Optional.empty();
                value = Optional.empty();
                date = Optional.empty();
            }
        }
        Optional<Integer> index = Optional.empty();
        return new LoanPredicate(parameter, index, value, date, operator);
    }

    /**
     * Parses {@code Collection<String> loanPredicates} into a {@code Set<LoanPredicates>}.
     */
    public static Set<LoanPredicate> parseLoanPredicates(Collection<String> preds) throws ParseException {
        requireNonNull(preds);
        final Set<LoanPredicate> predSet = new HashSet<>();
        for (String predName : preds) {
            predSet.add(parseLoanPredicate(predName));
        }
        return predSet;
    }
}
