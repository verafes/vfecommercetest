package utils;

import org.testng.ITestResult;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportUtils {
    private static final String LINE = "\n" + "_".repeat(100);
    private static final String LONG_TABS = "\t".repeat(10);
    private static final String SHORT_TABS = "\t".repeat(8);

    public static String getLine() {

        return LINE;
    }
    public static void logLine() {
        LoggerUtils.logInfo(LINE);
    }

    public static void logReportHeader() {
        final String header =
                """
                
                
                %sTEST RUN
                %sDATA: %s
                """.formatted(LONG_TABS, SHORT_TABS, getLocalDAteTime());
        LoggerUtils.logInfo(
                LINE + header + LINE
        );
    }

    private static String getLocalDAteTime(){
        LocalDateTime dateTimeNow = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-dd-MM, HH:mm");

        return dateTimeNow.format(dateTimeFormat);
    }

    public static void logTestName(Method method) {
        LoggerUtils.logInfo(getLine());
        String testInfo = """
        Run: %s""".formatted(getTestName(method));

        LoggerUtils.logInfo(testInfo);
    }

    public static String getTestName(Method method) {

        return method.getDeclaringClass().getSimpleName() + "." + method.getName();
    }

    public static String getTestRunTime(ITestResult testResult) {
        final long testRunTime = testResult.getEndMillis() - testResult.getStartMillis();

        int minutes = (int) ((testRunTime / 1000) / 60);
        int seconds = (int) (testRunTime / 1000) % 60;
        int milli = (int) (testRunTime % 1000) ;

        return "\t Run Time: " + minutes + " min " + seconds + " sec " + milli + " ms";
    }

    private static String getTestResult(ITestResult result) {

        return switch (result.getStatus()) {
            case 1 -> LoggerUtils.SUCCESS + " PASS";
            case 2 -> LoggerUtils.ERROR + " FAIL";
            case 3 -> LoggerUtils.WARNING + " SKIP";
            default -> "UNDEFINED";
        };
    }

    public static void logTestResult(Method method, ITestResult result) {
        LoggerUtils.logInfo("""
                
                
                %s %s %s %s""".formatted(
                        getTestName(method) + String.format("%" + (70 - getTestName(method).length()) + "s", " "),
                        getTestResult(result),
                        ReportUtils.getTestRunTime(result),
                        getLine()
                )
        );
    }
}
