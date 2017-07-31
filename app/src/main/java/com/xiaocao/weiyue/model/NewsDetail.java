package com.xiaocao.weiyue.model;

import java.util.List;

/**
 * className: NewsDetail
 * author: lijun
 * date: 17/7/6 11:45
 */

public class NewsDetail {
    private String body;
    private int replyCount;
    private String shareLink;
    private String digest;
    private String dkeys;
    private String ec;
    private String docid;
    private boolean picnews;
    private String title;
    private String tid;
    private String template;
    private int threadVote;
    private int threadAgainst;
    private String replyBoard;
    private String source;
    private boolean hasNext;
    private String voicecomment;
    private String ptime;
    private List<?> users;
    private List<?> ydbaike;
    private List<?> link;
    private List<?> votes;
    /**
     * ref : <!--IMG#0-->
     * pixel : 550*512
     * alt :
     * src : http://img1.cache.netease.com/catchpic/7/7F/7F9C353236E073FA3FD66708AFA58935.png
     */

    private List<ImgBean> img;
    /**
     * hasCover : true
     * subnum : 超过1000万
     * alias : Women
     * tname : 时尚
     * ename : nvren
     * tid : T1348650593803
     * cid : C1348650356377
     */

    private List<TopiclistNewsBean> topiclist_news;
    /**
     * hasCover : false
     * subnum : 41.3万
     * alias : 网易时尚
     * tname : 网易时尚
     * ename : T1436757113402
     * tid : T1436757113402
     * cid : C1374477741540
     */

    private List<TopiclistBean> topiclist;
    private List<?> boboList;
    private List<?> relative_sys;
    private List<?> apps;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDkeys() {
        return dkeys;
    }

    public void setDkeys(String dkeys) {
        this.dkeys = dkeys;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public boolean isPicnews() {
        return picnews;
    }

    public void setPicnews(boolean picnews) {
        this.picnews = picnews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getThreadVote() {
        return threadVote;
    }

    public void setThreadVote(int threadVote) {
        this.threadVote = threadVote;
    }

    public int getThreadAgainst() {
        return threadAgainst;
    }

    public void setThreadAgainst(int threadAgainst) {
        this.threadAgainst = threadAgainst;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getVoicecomment() {
        return voicecomment;
    }

    public void setVoicecomment(String voicecomment) {
        this.voicecomment = voicecomment;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<?> getUsers() {
        return users;
    }

    public void setUsers(List<?> users) {
        this.users = users;
    }

    public List<?> getYdbaike() {
        return ydbaike;
    }

    public void setYdbaike(List<?> ydbaike) {
        this.ydbaike = ydbaike;
    }

    public List<?> getLink() {
        return link;
    }

    public void setLink(List<?> link) {
        this.link = link;
    }

    public List<?> getVotes() {
        return votes;
    }

    public void setVotes(List<?> votes) {
        this.votes = votes;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public List<TopiclistNewsBean> getTopiclist_news() {
        return topiclist_news;
    }

    public void setTopiclist_news(List<TopiclistNewsBean> topiclist_news) {
        this.topiclist_news = topiclist_news;
    }

    public List<TopiclistBean> getTopiclist() {
        return topiclist;
    }

    public void setTopiclist(List<TopiclistBean> topiclist) {
        this.topiclist = topiclist;
    }

    public List<?> getBoboList() {
        return boboList;
    }

    public void setBoboList(List<?> boboList) {
        this.boboList = boboList;
    }

    public List<?> getRelative_sys() {
        return relative_sys;
    }

    public void setRelative_sys(List<?> relative_sys) {
        this.relative_sys = relative_sys;
    }

    public List<?> getApps() {
        return apps;
    }

    public void setApps(List<?> apps) {
        this.apps = apps;
    }

    public static class ImgBean {
        private String ref;
        private String pixel;
        private String alt;
        private String src;

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getPixel() {
            return pixel;
        }

        public void setPixel(String pixel) {
            this.pixel = pixel;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }

    public static class TopiclistNewsBean {
        private boolean hasCover;
        private String subnum;
        private String alias;
        private String tname;
        private String ename;
        private String tid;
        private String cid;

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public String getSubnum() {
            return subnum;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }

    public static class TopiclistBean {
        private boolean hasCover;
        private String subnum;
        private String alias;
        private String tname;
        private String ename;
        private String tid;
        private String cid;

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public String getSubnum() {
            return subnum;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }


//    public String body;
//    public int replyCount;
//    public String shareLink;
//    public String digest;
//    public String dkeys;
//    public String ec;
//    public String docid;
//    public boolean picnews;
//    public String title;
//    public String tid;
//    public String template;
//    public int threadVote;
//    public int threadAgainst;
//    public String replyBoard;
//    public String source;
//    public boolean hasNext;
//    public String voicecomment;
//    public String ptime;
//    public List<?> users;
//    public List<?> ydbaike;
//    public List<?> link;
//    public List<?> votes;
//    /**
//     * ref : <!--IMG#0-->
//     * pixel : 550*512
//     * alt :
//     * src : http://img1.cache.netease.com/catchpic/7/7F/7F9C353236E073FA3FD66708AFA58935.png
//     */
//
//    public List<ImgBean> img;
//    /**
//     * hasCover : true
//     * subnum : 超过1000万
//     * alias : Women
//     * tname : 时尚
//     * ename : nvren
//     * tid : T1348650593803
//     * cid : C1348650356377
//     */
//
//    public List<TopiclistNewsBean> topiclist_news;
//    /**
//     * hasCover : false
//     * subnum : 41.3万
//     * alias : 网易时尚
//     * tname : 网易时尚
//     * ename : T1436757113402
//     * tid : T1436757113402
//     * cid : C1374477741540
//     */
//
//    public List<TopiclistBean> topiclist;
//    public List<?> boboList;
//    public List<?> relative_sys;
//    public List<?> apps;
//
//
//    public static class ImgBean {
//        public String ref;
//        public String pixel;
//        public String alt;
//        public String src;
//
//    }
//
//    public static class TopiclistNewsBean {
//        public boolean hasCover;
//        public String subnum;
//        public String alias;
//        public String tname;
//        public String ename;
//        public String tid;
//        public String cid;
//
//    }
//
//    public static class TopiclistBean {
//        public boolean hasCover;
//        public String subnum;
//        public String alias;
//        public String tname;
//        public String ename;
//        public String tid;
//        public String cid;
//
//    }

}
