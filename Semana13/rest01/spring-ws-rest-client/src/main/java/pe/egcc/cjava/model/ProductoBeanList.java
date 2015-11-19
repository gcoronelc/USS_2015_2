package pe.egcc.cjava.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "productos")
public class ProductoBeanList {

  private List<ProductoBean> data;

  public List<ProductoBean> getData() {
    return data;
  }

  public void setData(List<ProductoBean> data) {
    this.data = data;
  }
}
