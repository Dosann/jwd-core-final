package com.epam.jwd.core_final.util;

public class ConsoleSample {

    public static void printMajorOptionsToChooseFrom() {
        System.out.println(" —— —— —— —— —— —— —— —— —— ——\n" +
                        "|  0. Exit the app            |\n" +
                        "|  1. Create                  |\n" +
                        "|  2. Update                  |\n" +
                        "|  3. View                    |\n" +
                        "|  4. Write in JSON file      |\n" +
                        " —— —— —— —— —— —— —— —— —— ——"
        );
    }

    public static void printEntitiesToChoseFrom() {
        System.out.println(" —— —— —— —— —— —— ——\n" +
                "|  1. Crew member    |\n" +
                "|  2. Spaceship      |\n" +
                "|  3. Flight mission |\n" +
                " —— —— —— —— —— —— ——"
        );
    }

    public static void printRolesToChoseFrom() {
        System.out.println(" —— —— —— —— —— —— —— ——\n" +
                "|  1. Mission specialist |\n" +
                "|  2. Flight engineer    |\n" +
                "|  3. Pilot              |\n" +
                "|  4. Commander          |\n" +
                " —— —— —— —— —— —— —— —— "
        );
    }

    public static void printRanksToChoseFrom() {
        System.out.println(" —— —— —— —— —— —— ——\n" +
                "|  1. Trainee        |\n" +
                "|  2. Second officer |\n" +
                "|  3. First officer  |\n" +
                "|  4. Captain        |\n" +
                " —— —— —— —— —— —— —— "
        );
    }

    public static void printEntityViewToChoseFrom() {
        System.out.println(" —— —— —— —— —— —— —— -\n" +
                "|  1. View all         |\n" +
                "|  2. Find by criteria |\n" +
                " —— —— —— —— —— —— —— —"
        );
    }

    public static void printMissionResultsToChooseFrom() {
        System.out.println(" —— —— —— —— —— ——\n" +
                "|  1. Completed    |\n" +
                "|  2. Planned      |\n" +
                "|  3. In progress  |\n" +
                "|  4. Cancelled    |\n" +
                "|  5. Failed       |\n" +
                " —— —— —— —— —— —— "
        );
    }

    public static void printSpaceshipAttributesToUpdate() {
        System.out.println(" —— —— —— —— —— —— —— —— —— —— —— ——\n" +
                "|  1. Crew members                   |\n" +
                "|  2. Flight distance                |\n" +
                "|  3. Preparation for next missions  |\n" +
                " —— —— —— —— —— —— —— —— —— —— —— —— "
        );
    }

    public static void printMissionAttributesToUpdate() {
        System.out.println(" —— —— —— —— —— —— ——\n" +
                "|  1. Start date     |\n" +
                "|  2. End date       |\n" +
                "|  3. Distance       |\n" +
                "|  4. Spaceship      |\n" +
                "|  5. Crew members   |\n" +
                "|  6. Mission result |\n" +
                " —— —— —— —— —— —— ——"
        );
    }

    public static void printWritingInJsonToChoseFrom() {
        System.out.println(" —— —— —— —— —— —— ——\n" +
                "|  1. Write multiple |\n" +
                "|  2. Write one      |\n" +
                " —— —— —— —— —— —— —— "
        );
    }

}
