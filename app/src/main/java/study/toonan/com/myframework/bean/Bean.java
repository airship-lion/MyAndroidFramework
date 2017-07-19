package study.toonan.com.myframework.bean;

/**
 * Created by Administrator on 2017/1/17.
 */
public class Bean {
    /**
     * id : 440113007
     * name : 市桥街
     */

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
