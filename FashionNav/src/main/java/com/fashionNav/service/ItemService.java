package com.fashionNav.service;

import com.fashionNav.model.dto.request.SaveItemRequest;
import com.fashionNav.model.dto.response.ItemImageDetail;
import com.fashionNav.model.dto.response.MainPageItemDetail;
import com.fashionNav.model.entity.Images;
import com.fashionNav.model.entity.Item;
import com.fashionNav.model.entity.ItemImage;
import com.fashionNav.repository.ImagesMapper;
import com.fashionNav.repository.ItemImageMapper;
import com.fashionNav.repository.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ImagesMapper imagesMapper;
    private final ItemImageMapper itemImageMapper;


    //메인페이지 Item
    public List<MainPageItemDetail> getAllMainPageItemDetails() {
        return itemMapper.getAllMainPageItemDetails();
    }
    @Transactional
    public void saveItemWithImage(SaveItemRequest saveItemRequest) {
        // Item 저장
        Item item = saveItemRequest.getItem().toEntity();
        itemMapper.insertItem(item);

        // Images 저장
        Images image = saveItemRequest.getImages().toEntity();
        imagesMapper.insertImage(image);

        // ItemImage 저장
        ItemImage itemImage = new ItemImage(item.getItemId(), image.getImageId(), saveItemRequest.isMain());
        itemImageMapper.insertItemImage(itemImage.getItemId(), itemImage.getImageId(), itemImage.isMain());
    }

    public List<Item> getAllItems() {
        return itemMapper.findAllItems();
    }

    public Item getItemById(int id) {
        return itemMapper.findItemById(id);
    }

    public List<ItemImageDetail> getAllItemImageDetails() {
        return itemImageMapper.getAllItemImageDetails();
    }

    public List<Item> getItemsByCategory(String style) {
        return itemMapper.findItemsByStyle(style);
    }

    public List<Item> getItemsByBrand(String brand) {
        return itemMapper.findItemsByBrand(brand);
    }

    @Transactional
    public void updateItem(Item item) {
        itemMapper.updateItem(item);
    }

    @Transactional
    public void deleteItem(int id) {
        itemMapper.deleteItem(id);
    }
}
