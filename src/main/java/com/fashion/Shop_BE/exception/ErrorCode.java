package com.fashion.Shop_BE.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(400, "Email đã tồn tại ", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(404, "Không tìm thấy User!", HttpStatus.NOT_FOUND),
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized exception!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(400, "Invalid key!", HttpStatus.BAD_REQUEST),
    INVALID_TYPE(400, "Invalid type!", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(400, "Email không đúng!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(400,"Email đã tồn tại, vui lòng nhập email khác", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(400, "Username phải có ít nhất {min} ký tự!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(400, "Password phải có ít nhất {min} ký tự!", HttpStatus.BAD_REQUEST),
    LOGIN_FAIL(400, "Đăng nhập thất bại!", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(401, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    PERMISSION_NOT_FOUND(404, "Quyền này không có!", HttpStatus.NOT_FOUND),
    INVALID_DATE_OF_BIRTH(400, "Tuổi của bạn phải tối thiểu là {min}!", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(400, "Token đã hết hạn!", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(400, "Token không hợp lệ!", HttpStatus.BAD_REQUEST),
    SEND_VERIFY_EMAIL_FAIL(400, "Gửi email xác thực tài khoản thất bại!", HttpStatus.BAD_REQUEST),
    ENABLE_FAIL(400,"Tài khoản của bạn chưa xác thực!", HttpStatus.BAD_REQUEST),
    DATE_OF_BIRTH_THAN_PAST(400,"Ngày sinh phải là ngày trong quá khứ!",HttpStatus.BAD_REQUEST),
    PHONE_INVALID(400,"Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_INVALID(400,"File phải là ảnh PNG hoặc JPG, không quá 5MB!",HttpStatus.BAD_REQUEST),
    UPLOAD_FILE_FAILED(400,"Upload ảnh thất bại!",HttpStatus.BAD_REQUEST),
    RESOURCE_NAME_NULL(400,"Không tìm thấy tên file sau khi upload!", HttpStatus.BAD_REQUEST),

    ROLE_NAME_REQUIRED(400,"Trường roleName không được bỏ trống!",HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(404, "Không tìm thấy Role!", HttpStatus.NOT_FOUND),

    //brand
    BRAND_NAME_UNVALID(400,"Tên thương hiệu không được bỏ trống!",HttpStatus.BAD_REQUEST),
    BRAND_CODE_UNVALID(400,"Trường code thương hiệu không được bỏ trống!",HttpStatus.BAD_REQUEST),
    BRAND_NAME_EXISTED(400,"Tên thương hiệu đã tồn tại!",HttpStatus.BAD_REQUEST),
    BRAND_NOT_FOUND(404,"Không tìm thấy thương hiệu!",HttpStatus.NOT_FOUND),
    BRAND_CONTAIN_PRODUCT(400,"Thương hiệu này đang chứa sản phẩm không thể xoá!",HttpStatus.BAD_REQUEST),
    BRAND_NOT_DELETED(400,"Thương hiêu này đang hoạt động!",HttpStatus.BAD_REQUEST),


    CATEGORY_NAME_NOT_BLANK(400,"Tên danh mục không được bỏ trống!",HttpStatus.BAD_REQUEST),
    CATEGORY_CODE_NOT_BLANK(400,"Trường code danh mục không được bỏ trống!",HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_EXISTED(400,"Tên danh mục đã tồn tại, vui lòng nhập tên khác!",HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(404,"Không tìm thấy danh mục !",HttpStatus.NOT_FOUND),
    CATEGORY_CONTAIN_CHILDREN_CATEGORY(400,"Danh mục này chứa danh mục con nên không được xoá!",HttpStatus.BAD_REQUEST),

    PRODUCT_NAME_NOT_BLANK(400,"Tên sản phẩm không được bỏ trống!",HttpStatus.BAD_REQUEST),
    BRAND_ID_NOT_NULL(400,"Trường brandId không được bỏ trống!",HttpStatus.BAD_REQUEST),
    CATEGORY_ID_NOT_NULL(400,"Trường categoryId không được bỏ trống!",HttpStatus.BAD_REQUEST),
    PRODUCT_SLUG_NOT_BLANK(400,"Slug sản phẩm không được bỏ trống!",HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_EXISTED(400,"Tên sản phẩm đã tồn tại! Vui lòng nhập tên khác!",HttpStatus.BAD_REQUEST),
    LIST_PRODUCT_VARIANT_NOT_BLANK(400,"Vui lòng nhập các biến thể của sản phẩm!", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(404,"Sản phẩm không tồn tại!",HttpStatus.NOT_FOUND),
    PRODUCT_CREATION_FAILED(400,"Thêm sản phẩm thất bại!",HttpStatus.BAD_REQUEST),
    INVALID_INPUT_PRODUCT_VARIANT(400,"Thêm biến thể sản phẩm thất bại!",HttpStatus.BAD_REQUEST),
    VARIANT_ID_NOT_BLANK(400,"Trường variantId bắt buộc phải có!",HttpStatus.BAD_REQUEST),
    PRODUCT_VARIANT_NOT_FOUND(404,"Biến thể sản phẩm không tìm thấy!",HttpStatus.NOT_FOUND),

    CART_QUANTITY_INVALID(400,"Số lượng phải lớn hơn 0",HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(404,"Giỏ hàng rỗng",HttpStatus.NOT_FOUND),
    CART_DETAIL_NOT_FOUND(404,"Sản phẩm không có trong giỏ hàng!",HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_NOT_ENOUGH_QUANTITY(400,"Số lượng yêu cầu vượt quá số lượng có sẵn!!",HttpStatus.BAD_REQUEST),
    PRODUCT_VARIANT_NOT_FOUND_IN_CART(404,"Biến thể sản phẩm không tìm thấy!",HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_IN_ORDER(400,"Sản phẩm đang có đơn hàng chưa hoàn tất, không thể xoá!",HttpStatus.BAD_REQUEST),

    DELIVERY_METHOD_NAME_NOT_BLANK(400,"Tên phương thức giao hàng không được bỏ trống!",HttpStatus.BAD_REQUEST),
    DELIVERY_METHOD_FEE_NOT_BLANK(400,"Phí giao hàng không được bỏ trống!",HttpStatus.BAD_REQUEST),
    DELIVERY_METHOD_NOT_FOUND(404,"Không tìm thấy phương thức giao hàng!",HttpStatus.NOT_FOUND),
    DELIVERY_METHOD_HAS_ACTIVE_ORDERS(400,"Phương thức giao hàng này đang có đơn hàng đang hoạt động!",HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_NAME_NOT_BLANK(400,"Tên phương thức thanh toán không được bỏ trống!",HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_NOT_FOUND(404,"Không tìm thấy phương thức thanh toán!",HttpStatus.NOT_FOUND),
    PAYMENT_METHOD_NOT_PAY_OS(400,"Phương thức thanh toán này không phải là PayOS!",HttpStatus.BAD_REQUEST),
    RECEIVER_NAME_NOT_BLANK(400,"Trường tên người nhận hàng không được bỏ trống!",HttpStatus.BAD_REQUEST),
    STREET_NOT_BLANK(400,"Tên Đường không được bỏ trống!",HttpStatus.BAD_REQUEST),
    WARD_NOT_BLANK(400,"Tên Xã không được bỏ trống!",HttpStatus.BAD_REQUEST),
    DISTRICT_NOT_BLANK(400,"Tên Huyện không được bỏ trống!",HttpStatus.BAD_REQUEST),
    CITY_NOT_BLANK(400,"Tên Thành Phố/Tỉnh không được bỏ trống!",HttpStatus.BAD_REQUEST),

    RECEIVER_ADDRESS_NOT_FOUND(404,"Không tìm thấy địa chỉ nhận hàng",HttpStatus.NOT_FOUND),

    VOUCHER_NOT_FOUND(404,"Không tìm thấy Voucher!",HttpStatus.NOT_FOUND),
    VOUCHER_CODE_NOT_BLANK(400,"Mã voucher không được để trống",HttpStatus.BAD_REQUEST),
    VOUCHER_INVALID(400,"Mã voucher không hợp lệ",HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_APPLIED(400,"Mã voucher không được áp dụng cho đơn hàng này",HttpStatus.BAD_REQUEST),
    DISCOUNT_TYPE_NOT_BLANK(400, "Loại giảm giá không được để trống",HttpStatus.BAD_REQUEST),
    DISCOUNT_VALUE_NOT_NULL(400, "Giá trị giảm giá không được để trống",HttpStatus.BAD_REQUEST),
    DISCOUNT_VALUE_BIGGER_THAN_0(400,"Giá trị giảm giá phải lớn hơn hoặc bằng 0",HttpStatus.BAD_REQUEST),
    MIN_ORDER_VALUE_BIGGER_THAN_0(400, "Giá trị đơn hàng tối thiểu phải lớn hơn hoặc bằng 0", HttpStatus.BAD_REQUEST),
    MAX_ORDER_VALUE_BIGGER_THAN_0(400, "Giá trị đơn hàng tối đa phải lớn hơn hoặc bằng 0", HttpStatus.BAD_REQUEST),
    START_DATE_NOT_NULL(400,"Ngày bắt đầu không được để trống" , HttpStatus.BAD_REQUEST),
    START_DATE_INVALID(400,"Ngày bắt đầu phải là hiện tại hoặc tương lai" , HttpStatus.BAD_REQUEST),
    END_DATE_NOT_NULL(400, "Ngày kết thúc không được để trống" , HttpStatus.BAD_REQUEST),
    END_DATE_INVALID(400,"Ngày kết thúc phải là tương lai", HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(400, "Số lượng phải lớn hơn hoặc bằng 0" , HttpStatus.BAD_REQUEST),


    RECEIVER_ADDRESS_REQUIRED(400,"Địa chỉ nhận hàng không được để trống",HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_REQUIRED(400,"Phương thức thanh toán không được để trống",HttpStatus.BAD_REQUEST),
    DELIVERY_METHOD_REQUIRED(400,"Phương thức giao hàng không được để trống",HttpStatus.BAD_REQUEST),
    ORDER_DETAIL_REQUIRED(400,"Danh sách sản phẩm không được để trống",HttpStatus.BAD_REQUEST),
    PRODUCT_VARIANT_ID_REQUIRED(400,"ID biến thể sản phẩm không được để trống",HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(404,"Không tìm thấy đơn hàng",HttpStatus.NOT_FOUND),
    ORDER_ALREADY_CANCELLED(400,"Đơn hàng đã được huỷ",HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_DELIVERED(400,"Đơn hàng đã được giao",HttpStatus.BAD_REQUEST),
    INVALID_ORDER_STATUS(400,"Trạng thái đơn hàng không hợp lệ",HttpStatus.BAD_REQUEST),

    PAYMENT_NOT_FOUND(404,"Không tìm thấy thông tin thanh toán",HttpStatus.NOT_FOUND),

    REVIEW_NOT_FOUND(404,"Không tìm thấy đánh giá",HttpStatus.NOT_FOUND),
    SEND_EMAIL_FAIL(400, "Gửi email thất bại!", HttpStatus.BAD_REQUEST)
    ;
    private int code;
    private String message;
    private HttpStatus httpStatus;
}
