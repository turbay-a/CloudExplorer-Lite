package com.fit2cloud.controller.request.host;

import com.fit2cloud.request.pub.OrderRequest;
import com.fit2cloud.request.pub.PageOrderRequestInterface;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;

/**
 * @author jianneng
 * @date 2022/12/11 18:46
 **/
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
public class PageHostRequest extends HostRequest implements PageOrderRequestInterface {

    @Serial
    private static final long serialVersionUID = 1562817563718863118L;

    @Schema(title = "当前页", required = true)
    @Min(value = 0, message = "{i18n.page.current_page.min}")
    @NotNull(message = "{i18n.page.current_page.is.not.empty}")
    private Integer currentPage;

    @Schema(title = "每页大小", required = true)
    @Min(value = 0, message = "{i18n.page.page.size.min}")
    @NotNull(message = "{i18n.page.page.size.is.not.empty}")
    private Integer pageSize;

    @Schema(title = "排序", example = " {\"column\":\"createTime\",\"asc\":false}")
    private OrderRequest order;

}
