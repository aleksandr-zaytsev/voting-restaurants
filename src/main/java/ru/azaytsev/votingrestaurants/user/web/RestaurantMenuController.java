//package ru.azaytsev.votingrestaurants.user.web;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import ru.azaytsev.votingrestaurants.user.service.RestaurantMenuService;
//import ru.azaytsev.votingrestaurants.user.to.RestaurantListTo;
//
//@RestController
//@RequestMapping(value = RestaurantMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
//@Slf4j
//@AllArgsConstructor
//public class RestaurantMenuController {
//
//    public RestaurantMenuService restaurantMenuService;
//    static final String REST_URL = "/api/v1/restaurants";
//
//    @GetMapping("/all")
//    public ResponseEntity<RestaurantListTo> getAll() {
//        return ResponseEntity.of(restaurantMenuService.getAll());
//    }
//
//}
