package org.exoplatform.samples.services;

public class SampleService {
    public String getMessage() {
        return "Hello from SampleService!";
    }
    public String getGreeting(String name) {
        return "Hello, " + name + "!";
    }
    public int add(int a, int b) {
        return a + b;
    }
    public String getCurrentTime() {
        return java.time.LocalTime.now().toString();
    }
    public String getDate() {
        return java.time.LocalDate.now().toString();
    }
    public String getDateTime() {
        return java.time.LocalDateTime.now().toString();
    }
    public String getRandomQuote() {
        String[] quotes = {
            "The only limit to our realization of tomorrow is our doubts of today.",
            "Do not wait to strike till the iron is hot, but make it hot by striking.",
            "The future belongs to those who believe in the beauty of their dreams.",
            "It does not matter how slowly you go as long as you do not stop.",
            "Success is not final, failure is not fatal: It is the courage to continue that counts."
        };
        int index = (int) (Math.random() * quotes.length);
        return quotes[index];
    }
    public static void main(String[] args) {
        SampleService service = new SampleService();
        System.out.println(service.getMessage());
        System.out.println(service.getGreeting("World"));
        System.out.println("Sum: " + service.add(5, 10));
        System.out.println("Current Time: " + service.getCurrentTime());
        System.out.println("Current Date: " + service.getDate());
        System.out.println("Current DateTime: " + service.getDateTime());
        System.out.println("Random Quote: " + service.getRandomQuote());
    }
}


