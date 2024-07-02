import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class QuizGenerator {
    private Map<String, Quiz> quizzes;
    private Scanner scanner;

    public QuizGenerator() {
        quizzes = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("Quiz Generator");
            System.out.println("-------------");
            System.out.println("1. Create Quiz");
            System.out.println("2. Take Quiz");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    createQuiz();
                    break;
                case 2:
                    takeQuiz();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private void createQuiz() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine();
        Quiz quiz = new Quiz(quizName);
        quizzes.put(quizName, quiz);

        while (true) {
            System.out.print("Enter question (or 'done' to finish): ");
            String question = scanner.nextLine();
            if (question.equalsIgnoreCase("done")) {
                break;
            }
            quiz.addQuestion(question);
            System.out.print("Enter options (comma-separated): ");
            String options = scanner.nextLine();
            quiz.addOptions(question, options);
            System.out.print("Enter correct answer: ");
            String correctAnswer = scanner.nextLine();
            quiz.setCorrectAnswer(question, correctAnswer);
        }
    }

    private void takeQuiz() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine();
        Quiz quiz = quizzes.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found!");
            return;
        }

        int score = 0;
        for (Question question : quiz.getQuestions()) {
            System.out.println(question.getQuestion());
            System.out.println("Options: " + question.getOptions());
            System.out.print("Enter your answer: ");
            String userAnswer = scanner.nextLine();
            if (userAnswer.equalsIgnoreCase(question.getCorrectAnswer())) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. Correct answer: " + question.getCorrectAnswer());
            }
        }

        System.out.println("Your score: " + score + "/" + quiz.getQuestions().size());
    }

    public static void main(String[] args) {
        QuizGenerator quizGenerator = new QuizGenerator();
        quizGenerator.run();
    }
}

class Quiz {
    private String name;
    private List<Question> questions;

    public Quiz(String name) {
        this.name = name;
        questions = new ArrayList<>();
    }

    public void addQuestion(String question) {
        questions.add(new Question(question));
    }

    public void addOptions(String question, String options) {
        Question q = getQuestion(question);
        q.setOptions(options.split(","));
    }

    public void setCorrectAnswer(String question, String correctAnswer) {
        Question q = getQuestion(question);
        q.setCorrectAnswer(correctAnswer);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    private Question getQuestion(String question) {
        for (Question q : questions) {
            if (q.getQuestion().equals(question)) {
                return q;
            }
        }
        return null;
    }
}

class Question {
    private String question;
    private String[] options;
    private String correctAnswer;

    public Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}