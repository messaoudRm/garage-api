package library;

/** Le profil arrive du formulaire, en texte. Cette signature ne change pas. */
public class LoanDesk {

    public int daysAllowed(String profile) {
        switch (profile) {
            case "student":
                return 30;
            case "teacher":
                return 60;
            default:
                return 15;
        }
    }
}
