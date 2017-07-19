package study.toonan.com.myframework.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
public class RentPatrol {


    private RentPatrolInner vo;
    /**
     * url : 6_1482916956598.jpg
     */

    private List<AttachmentListBean> attachmentList;

    public RentPatrolInner getVo() {
        return vo;
    }

    public void setVo(RentPatrolInner vo) {
        this.vo = vo;
    }

    public List<AttachmentListBean> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<AttachmentListBean> attachmentList) {
        this.attachmentList = attachmentList;
    }


    public static class AttachmentListBean {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
