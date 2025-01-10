packagecom. import org.springframework.web.bind.annotation.*;
@RestCotroller
@RequestMapping("/target-service")
public class TargetController {

    @PostMapping("/endpoint")
    public String handleMessage(@RequestBody String message) {
        System.out.println("Message received at target service: " + message);
        return "Message processed successfully";
    }
}