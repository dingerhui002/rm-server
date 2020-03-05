package com.bc.rm.server.entity.electronic;

import lombok.Data;

import java.util.List;

/**
 * @author d
 */
@Data
public class Position {
    /**
     * 页数
     */
    private String pageIndex;

    private List<Coordinate> coordinateList;
}
