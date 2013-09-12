//package com.utopia.watchout.helpers;
//
///**
// * Created by Doheum on 13. 9. 4.
// */
//import java.util.List;
//import retrofit.http.GET;
//import retrofit.http.Path;
//import retrofit.RestAdapter;
//
//public class ClientHelper {
//    private static final String API_URL = "http://watchout.herokuapp.com/";
//    private RestAdapter restAdapter;
//    private WOService wo;
//    static class User {
//        String username;
//        String email;
//        String password;
//        String createdAt;
//        String updatedAt;
//        String id;
//    }
//
//    interface WOService {
//        @GET("/users")
//        List<User> users();
//    }
//
//    public ClientHelper() {
//        // Create a very simple REST adapter which points http://watchout.herokuapp.com/.
//        restAdapter = new RestAdapter.Builder()
//                .setServer(API_URL)
//                .build();
//
//        // Create an instance of our WatchOut API interface.
//        wo = restAdapter.create(WOService.class);
//    }
//
//    public void printUsers()
//    {
//        // Fetch and print a list of the contributors to this library.
//        List<User> users = wo.users(); //problem here
//        for (User one : users) {
//            System.out.println(one.username + " (" + one.email + ")");
//        }
//    }
//
//}
