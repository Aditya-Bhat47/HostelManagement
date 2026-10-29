package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.MealAttendance;
import com.example.HostelManagement.entity.MenuItem;
import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.MealType;
import com.example.HostelManagement.enums.Status;
import com.example.HostelManagement.exceptionHandler.DuplicateResourceException;
import com.example.HostelManagement.exceptionHandler.ResourceNotFoundexception;
import com.example.HostelManagement.records.MealAttendanceResponse;
import com.example.HostelManagement.records.MenuItemResponse;
import com.example.HostelManagement.records.MessMealRequest;
import com.example.HostelManagement.records.MessMealResponse;
import com.example.HostelManagement.repository.MealAttendanceRepo;
import com.example.HostelManagement.repository.MenuItemRepo;
import com.example.HostelManagement.repository.MessMealRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessMealService {
    private final MessMealRepo messMealRepo;
    private final MealAttendanceRepo mealAttendanceRepo;
    private final MenuItemRepo menuItemRepo;

    public ResponseEntity<MessMealResponse> createMeal(MessMealRequest request){
        MessMeal meal=toMessMeal(request);
        meal=messMealRepo.save(meal);
        return ResponseEntity.ok(toMessMealResponse(meal));
    }

    public ResponseEntity<MessMealResponse> getMealById(Long id){
        MessMeal meal=messMealRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Meal Not found"));
        return ResponseEntity.ok(toMessMealResponse(meal));
    }

    public ResponseEntity<Page<MessMealResponse>> getAllMeals(Pageable pageable){
        Page<MessMeal> meals=messMealRepo.findAll(pageable);
        Page<MessMealResponse> responses=meals.map(this::toMessMealResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<MessMealResponse> updateMeal(
            Long id,
            MessMealRequest request) {

        MessMeal meal = messMealRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Meal not found"));

        meal.setMealType(request.mealType());
        meal.setMealDate(request.mealDate());
        meal.setOpenTime(request.openTime());
        meal.setCloseTime(request.closeTime());

        List<MenuItem> menuItems =
                menuItemRepo.findAllById(request.menuItemIds());

        if (menuItems.size() != request.menuItemIds().size()) {
            throw new ResourceNotFoundexception(
                    "One or more menu items not found");
        }

        meal.setMenuItems(menuItems);

        meal = messMealRepo.save(meal);

        return ResponseEntity.ok(toMessMealResponse(meal));
    }

    public ResponseEntity<String> delMeal(Long id){
        MessMeal meal=messMealRepo.findById(id).orElseThrow(()->new ResourceNotFoundexception("Meal not found"));
        messMealRepo.delete(meal);
        return ResponseEntity.ok("Record Deleted ");
    }

    public ResponseEntity<Page<MessMealResponse>> getMealsByDate(LocalDate mealDate,Pageable pageable){
        Page<MessMeal> meals=messMealRepo.findByMealDate(mealDate,pageable);
        Page<MessMealResponse> responses=meals.map(this::toMessMealResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MessMealResponse>> getMealsBetweenDates(LocalDate startDate,
                                                                       LocalDate endDate,
                                                                       Pageable pageable){
        Page<MessMeal> meals=messMealRepo.findByMealDateBetween(startDate,endDate,pageable);
        Page<MessMealResponse> responses=meals.map(this::toMessMealResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MessMealResponse>> getTodayMeals(Pageable pageable){
        Page<MessMeal> meals=messMealRepo.findByMealDate(LocalDate.now(),pageable);
        Page<MessMealResponse> responses=meals.map(this::toMessMealResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MessMealResponse>> getMealsByType(MealType mealType,Pageable pageable){
        Page<MessMeal> meals=messMealRepo.findByMealType(mealType,pageable);
        Page<MessMealResponse> responses=meals.map(this::toMessMealResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MessMealResponse>> getMealForDateAndType(LocalDate mealDate,MealType mealType,Pageable pageable){
        Page<MessMeal> meals=messMealRepo.findByMealDateAndMealType(mealDate,mealType,pageable);
        Page<MessMealResponse> responses=meals.map(this::toMessMealResponse);
        return ResponseEntity.ok(responses);

    }

    public ResponseEntity<String> addMenuItemToMeal(Long mealId,Long menuItemId){
        MessMeal meal=messMealRepo.findById(mealId)
                .orElseThrow(()->new ResourceNotFoundexception("Meal not found"));
        MenuItem menuItem=menuItemRepo.findById(menuItemId)
                .orElseThrow(()->new ResourceNotFoundexception("Menu item not found"));
        meal.getMenuItems().add(menuItem);
        if (meal.getMenuItems().contains(menuItem)) {
            throw new DuplicateResourceException("Menu item already added to this meal");
        }
        messMealRepo.save(meal);
        return ResponseEntity.ok("Menu item added to meal");
    }

    public ResponseEntity<String> removeMenuItemFromMeal(Long mealId,Long menuItemId){
        MessMeal meal=messMealRepo.findById(mealId)
                .orElseThrow(()->new ResourceNotFoundexception("Meal not found"));
        MenuItem menuItem=menuItemRepo.findById(menuItemId)
                .orElseThrow(()->new ResourceNotFoundexception("Menu item not found"));

        if (!meal.getMenuItems().contains(menuItem)) {
            throw new ResourceNotFoundexception("Menu item is not part of this meal");
        }
        meal.getMenuItems().remove(menuItem);
        messMealRepo.save(meal);
        return ResponseEntity.ok("Menu item removed");
    }

    public ResponseEntity<List<MenuItemResponse>> getMenuItemsForMeal(Long mealId){
        MessMeal meal=messMealRepo.findById(mealId)
                .orElseThrow(()->new ResourceNotFoundexception("Meal not found"));
      List<MenuItemResponse> responses=meal.getMenuItems()
              .stream().map(this::toMenuItemResponse).toList();
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<MessMealResponse>> getCurrentlyOpenMeals() {

        List<MessMeal> meals = messMealRepo.findByMealDate(LocalDate.now());

        List<MessMealResponse> responses = meals.stream()
                .filter(MessMeal::isOpenNow)
                .map(this::toMessMealResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Boolean> isMealCurrentlyOpen(){
        List<MessMeal> meals=messMealRepo.findByMealDate(LocalDate.now());
        boolean isOpen=meals.stream().anyMatch(MessMeal::isOpenNow);
        return ResponseEntity.ok(isOpen);
    }

    public ResponseEntity<MealAttendanceResponse> getAttendanceForMeal(Long mealId,Long attendanceId){
        MessMeal meal=messMealRepo.findById(mealId).orElseThrow(
                ()->new ResourceNotFoundexception("Meal not found")
        );
        MealAttendance attendance=mealAttendanceRepo.findById(attendanceId)
                .orElseThrow(()->new ResourceNotFoundexception("Attendance not found"));
        if (!attendance.getMeal().getId().equals(meal.getId())){
            throw new ResourceNotFoundexception("Attendance does not belong to this meal");
        }
        return ResponseEntity.ok(toMealAttendanceResponse(attendance));
    }

    public ResponseEntity<Long> getAttendanceCount(){
        long count=mealAttendanceRepo.count();
        return ResponseEntity.ok(count);
    }

    public ResponseEntity<List<Student>> getAttendingStudents() {

        List<Student> students =
                messMealRepo.findAttendingStudents(Status.ATTENDED);

        return ResponseEntity.ok(students);
    }

    public MealAttendanceResponse toMealAttendanceResponse(MealAttendance mealAttendance){
        return new MealAttendanceResponse(
                mealAttendance.getId(),
                mealAttendance.getMeal().getId(),
                mealAttendance.getStudent().getId(),
                mealAttendance.getStatus(),
                mealAttendance.getCheckedInAt()
        );
    }
    public MessMeal toMessMeal(MessMealRequest request){
        MessMeal meal=new MessMeal();
        meal.setMealType(request.mealType());
        meal.setMealDate(request.mealDate());
        meal.setCloseTime(request.closeTime());
        meal.setOpenTime(request.openTime());
        List<MenuItem> menuItemList=menuItemRepo.findAllById(request.menuItemIds());
        if (menuItemList.size() != request.menuItemIds().size()){
            throw new ResourceNotFoundexception("One or more items not found");
        }
        meal.setMenuItems(menuItemList);
        return meal;
    }

    public MessMealResponse toMessMealResponse(MessMeal meal){
        return new MessMealResponse(
                meal.getId(),
                meal.getMealDate(),
                meal.getMealType(),
                meal.getOpenTime(),
                meal.getCloseTime(),
                meal.getMenuItems()
                        .stream().map(this::toMenuItemResponse)
                        .toList()
        );
    }
    public MenuItemResponse toMenuItemResponse(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.isVegetarian(),
                menuItem.getCategory(),
                menuItem.isActive()
        );
    }
}
