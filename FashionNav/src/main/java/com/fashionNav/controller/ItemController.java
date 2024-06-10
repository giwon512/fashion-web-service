package com.fashionNav.controller;


import com.fashionNav.model.dto.request.SaveItemRequest;
import com.fashionNav.model.dto.response.ItemImageDetail;
import com.fashionNav.model.dto.response.MainPageItemDetail;
import com.fashionNav.model.entity.Item;
import com.fashionNav.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item API", description = "상품 관련 API")
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "메인 페이지에서의 아이템 리스트", description = "메인에 표시될 상품 정보를 조회합니다.")
    @GetMapping("/summaries")
    public List<MainPageItemDetail> getAllMainPageItem() {
        return  itemService.getAllMainPageItemDetails();
    }

    @Operation(summary = "상품 및 이미지 저장", description = "상품 정보를 이미지와 함께 저장합니다.")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveItemWithImage(@RequestBody SaveItemRequest saveItemRequest) {
        itemService.saveItemWithImage(saveItemRequest);
    }

    @Operation(summary = "상품 업데이트", description = "기존 상품의 정보를 업데이트합니다.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateItem(@RequestBody Item item, @PathVariable int id) {
        item.setItemId(id);
        itemService.updateItem(item);
    }

    @Operation(summary = "상품 삭제", description = "특정 ID를 가진 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
    }

    @Operation(summary = "모든 상품 조회", description = "모든 상품을 조회합니다.")
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @Operation(summary = "상품 ID로 조회", description = "특정 ID를 가진 상품을 조회합니다.")
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable int id) {
        return itemService.getItemById(id);
    }

    @Operation(summary = "모든 상품 이미지 상세 조회", description = "모든 상품 이미지 상세 정보를 조회합니다.")
    @GetMapping("/details")
    public List<ItemImageDetail> getAllItemImageDetails() {
        return itemService.getAllItemImageDetails();
    }

    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리의 상품을 조회합니다.")
    @GetMapping("/category/{style}")
    public List<Item> getItemsByCategory(@PathVariable String style) {
        return itemService.getItemsByCategory(style);
    }

    @Operation(summary = "브랜드별 상품 조회", description = "특정 브랜드의 상품을 조회합니다.")
    @GetMapping("/brand/{brand}")
    public List<Item> getItemsByBrand(@PathVariable String brand) {
        return itemService.getItemsByBrand(brand);
    }
}
