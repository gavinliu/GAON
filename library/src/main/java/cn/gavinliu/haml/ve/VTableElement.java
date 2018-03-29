package cn.gavinliu.haml.ve;

import java.util.List;

public class VTableElement extends IVirtualElement {

    public VTableElement(int row, int col, List<List<IVirtualElement>> items) {
        this.row = row;
        this.col = col;
        this.items = items;
    }

    public int row;

    public int col;

    public List<List<IVirtualElement>> items;
}
