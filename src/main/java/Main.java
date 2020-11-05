import dao.DaoImplementaion;
import entities.Discipline;
import entities.Role;
import entities.Task;
import entities.User;
import enums.Status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    private static DaoImplementaion dao = new DaoImplementaion();

    public static void main (String [] args) {

        List<Role> roles = roleFactory();
        dao.toDatabase(roles);

        List<Task> tasks = taskFactory();
        dao.toDatabase(tasks);

        List<User> users = userFactory();
        dao.toDatabase(users);

        List<Discipline> disciplines = disciplineFacotry();
        dao.toDatabase(disciplines);

        //Add head to disciplines
        disciplines.get(0).setHeadOfDiscipline(users.get(0));
        disciplines.get(1).setHeadOfDiscipline(users.get(1));
        disciplines.get(2).setHeadOfDiscipline(users.get(2));

        dao.updateDatabase(disciplines);

        //Add discipline to users

        disciplines.get(0).addMembers(users.get(0));
        disciplines.get(0).addMembers(users.get(3));
        disciplines.get(0).addMembers(users.get(6));
        disciplines.get(0).addMembers(users.get(7));

        disciplines.get(1).addMembers(users.get(1));
        disciplines.get(1).addMembers(users.get(4));

        disciplines.get(2).addMembers(users.get(2));
        disciplines.get(2).addMembers(users.get(5));

        dao.updateDatabase(disciplines);

        //Add roles to users
        Set<Role> user0Roles = new HashSet<>();
        user0Roles.add(roles.get(1));
        user0Roles.add(roles.get(3));
        users.get(0).setRoles(user0Roles);

        Set<Role> user1Roles = new HashSet<>();
        user1Roles.add(roles.get(0));
        user1Roles.add(roles.get(2));
        user1Roles.add(roles.get(3));
        user1Roles.add(roles.get(6));
        users.get(1).setRoles(user1Roles);

        Set<Role> user2Roles = new HashSet<>();
        user2Roles.add(roles.get(1));
        user2Roles.add(roles.get(2));
        users.get(2).setRoles(user2Roles);

        Set<Role> user3Roles = new HashSet<>();
        user3Roles.add(roles.get(5));
        users.get(3).setRoles(user3Roles);

        Set<Role> user4Roles = new HashSet<>();
        user4Roles.add(roles.get(4));
        user4Roles.add(roles.get(1));
        user4Roles.add(roles.get(0));
        users.get(4).setRoles(user4Roles);

        Set<Role> user5Roles = new HashSet<>();
        user5Roles.add(roles.get(2));
        user5Roles.add(roles.get(6));
        users.get(5).setRoles(user5Roles);

        Set<Role> user6Roles = new HashSet<>();
        user6Roles.add(roles.get(5));
        user6Roles.add(roles.get(2));
        users.get(6).setRoles(user6Roles);

        Set<Role> user7Roles = new HashSet<>();
        user7Roles.add(roles.get(5));
        users.get(7).setRoles(user7Roles);

        dao.updateDatabase(users);

        //Add tasks to users
        Set<Task> user0Tasks = new HashSet<>();
        user0Tasks.add(tasks.get(0));
        user0Tasks.add(tasks.get(1));
        users.get(0).setTasks(user0Tasks);

        Set<Task> user1Tasks = new HashSet<>();
        user1Tasks.add(tasks.get(2));
        user1Tasks.add(tasks.get(3));
        users.get(1).setTasks(user1Tasks);

        Set<Task> user2Tasks = new HashSet<>();
        user2Tasks.add(tasks.get(4));
        users.get(2).setTasks(user2Tasks);

        Set<Task> user3Tasks = new HashSet<>();
        user3Tasks.add(tasks.get(5));
        users.get(3).setTasks(user3Tasks);

        Set<Task> user4Tasks = new HashSet<>();
        user4Tasks.add(tasks.get(6));
        users.get(4).setTasks(user4Tasks);

        Set<Task> user5Tasks = new HashSet<>();
        user5Tasks.add(tasks.get(7));
        users.get(5).setTasks(user5Tasks);

        Set<Task> user6Tasks = new HashSet<>();
        user6Tasks.add(tasks.get(8));
        users.get(6).setTasks(user6Tasks);

        Set<Task> user7Tasks = new HashSet<>();
        user7Tasks.add(tasks.get(9));
        users.get(7).setTasks(user7Tasks);

        dao.updateDatabase(users);

        //Select all USERS that have introduced role
        List<User> usersWithSameRole = dao.getUsersByRole("Manager");
        System.out.println();
        System.out.println("------------------------Users form introduced role are: ");
        for(User u : usersWithSameRole) {
            System.out.println(u);
        }
        System.out.println();

        //Select all USERS from AM
        List<User> usersWithSameDiscipline = dao.getUsersByDiscipline("AM");
        System.out.println();
        System.out.println("------------------------Users form introduced discipline are: ");
        for(User u : usersWithSameDiscipline) {
            System.out.println(u);
        }
        System.out.println();

        //Select all USERS that have task in TO DO
        List<User> usersWithSameTaskStatus = dao.getUsersByTaskStatus(Status.TODO);
        System.out.println();
        System.out.println("------------------------Users with task status TODO are: ");
        for(User u : usersWithSameTaskStatus) {
            System.out.println(u);
        }
        System.out.println();

        //Change head of discipline DEV
        dao.updateHeadOfDiscipline(users.get(3), "DEV");

        //Select all discipline with 2 or less members
        List<Discipline> disciplinesWith2OrLessUsers = dao.getDisciplineWith2OrLessUsers();
        System.out.println();
        System.out.println("------------------------Disciplines with 2 or less users are: ");
        for(Discipline d : disciplinesWith2OrLessUsers) {
            System.out.println(d);
        }
        System.out.println();

        //Soft delete an User
        dao.softDeleteUser(users.get(7).getId());

    }

    public static List<Role> roleFactory(){
        List<Role> list = new ArrayList<>();
        list.add(new Role("Executive"));
        list.add(new Role("Manager"));
        list.add(new Role("Ceo"));
        list.add(new Role("HSP"));
        list.add(new Role("Engineer"));
        list.add(new Role("Architect"));
        list.add(new Role("Analyst"));
        return list;
    }

    public static List<Task> taskFactory(){
        List<Task> list = new ArrayList<>();
        list.add(new Task("Test UI", "Test UI part", Date.valueOf("2020-05-06"),
                Date.valueOf("2020-05-16"), Status.PROGRESS));
        list.add(new Task("Main Form Button", "Do button from main form", Date.valueOf("2020-05-02"),
                Date.valueOf("2020-05-04"), Status.PROGRESS));
        list.add(new Task("Login Page", "Do login page", Date.valueOf("2020-05-05"),
                Date.valueOf("2020-05-10"), Status.TODO));
        list.add(new Task("Employee status", "Check employee status", Date.valueOf("2020-04-25"),
                Date.valueOf("2020-05-25"), Status.PROGRESS));
        list.add(new Task("Cabinet UI", "Do UI for cabinet page", Date.valueOf("2020-05-01"),
                Date.valueOf("2020-05-4"), Status.DONE));
        list.add(new Task("Annual earnings", "Calculate earnings for this year", Date.valueOf("2020-05-10"),
                Date.valueOf("2020-05-13"), Status.TODO));
        list.add(new Task("Test log", "Test how log works", Date.valueOf("2020-05-09"),
                Date.valueOf("2020-05-19"), Status.PROGRESS));
        list.add(new Task("Project status", "Check project current status", Date.valueOf("2020-05-04"),
                Date.valueOf("2020-05-16"), Status.TODO));
        list.add(new Task("Database", "Create database", Date.valueOf("2020-05-02"),
                Date.valueOf("2020-05-06"), Status.DONE));
        list.add(new Task("Tables", "Create tables", Date.valueOf("2020-05-01"),
                Date.valueOf("2020-05-05"), Status.DONE));
        return list;
    }

    public static List<Discipline> disciplineFacotry(){
        List<Discipline> list = new ArrayList<>();
        list.add(new Discipline("DEV"));
        list.add(new Discipline("TEST"));
        list.add(new Discipline("AM"));
        return list;
    }

    public static List<User> userFactory(){
        List<User> list = new ArrayList<>();

        list.add(new User("Ion", "Baciu", "ion.baciu@mail.com", "ibaciu", Date.valueOf("2018-02-02"), true));
        list.add(new User("Ana", "Baciu", "ana.baciu@mail.com", "abaciu", Date.valueOf("2019-02-02"), true));
        list.add(new User("Ala", "Negura", "ala.negura@mail.com", "anegura", Date.valueOf("2015-02-02"), true));
        list.add(new User("Alex", "Negura", "alex.negura@mail.com", "alnegura", Date.valueOf("2012-02-02"), true));
        list.add(new User("Ana", "Flex", "ana.flex@mail.com", "aflex", Date.valueOf("2012-12-22"), true));
        list.add(new User("Mihai", "Iovu", "mihai.iovu@mail.com", "miovu", Date.valueOf("2019-09-12"), true));
        list.add(new User("Pavel", "Popa", "pavel.popa@mail.com", "ppopa", Date.valueOf("2012-10-12"), true));
        list.add(new User("Mihai", "Popa", "mihai.popa@mail.com", "mpopa", Date.valueOf("2019-09-09"), true));
        return list;
    }
}
