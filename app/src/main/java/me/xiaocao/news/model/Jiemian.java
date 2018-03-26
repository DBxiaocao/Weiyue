package me.xiaocao.news.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * description: Jiemian
 * author: lijun
 * date: 18/1/4 20:08
 */

public class Jiemian {

    private String pageCount;
    private String page;
    private String lastTime;
    private List<CarouselEntity> carousel;
    private List<ListEntityX> list;

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public List<CarouselEntity> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<CarouselEntity> carousel) {
        this.carousel = carousel;
    }

    public List<ListEntityX> getList() {
        return list;
    }

    public void setList(List<ListEntityX> list) {
        this.list = list;
    }

    public static class CarouselEntity {
        /**
         * article : {"ar_id":"1860995","ar_tl":"【评论】支付宝\u201c年度账单\u201d事件：大公司别做小动作","ar_image":"https://img2.jiemian.com/101/original/20180104/151505971114634400_a750x422.jpg"}
         * type : article
         * ads : [{"i_show_tpl":"banner_img","ad_tl":"2018年1月24日 来上海见证\u201c10大新时代商业领袖\u201d","ad_name":"广告","ad_tm":"0","ad_url":"http://www.jiemian.com/article/1852471.html","ad_img":"https://img.jiemian.com/ads/20171231/151469168459915700.jpg","ad_open_type":"1","ad_size":"640*360","ad_wid":"7881","ad_aid":"5823","ad_position":"ASHOUYE","ad_msurl":""}]
         */

        private ArticleEntity article;
        private String type;
        private List<AdsEntity> ads;

        public ArticleEntity getArticle() {
            if (article != null)
                return article;
            else
                return new ArticleEntity();
        }

        public void setArticle(ArticleEntity article) {
            this.article = article;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<AdsEntity> getAds() {
            return ads;
        }

        public void setAds(List<AdsEntity> ads) {
            this.ads = ads;
        }

        public static class ArticleEntity {
            /**
             * ar_id : 1860995
             * ar_tl : 【评论】支付宝“年度账单”事件：大公司别做小动作
             * ar_image : https://img2.jiemian.com/101/original/20180104/151505971114634400_a750x422.jpg
             */

            private String ar_id;
            private String ar_tl;
            private String ar_image;

            public String getAr_id() {
                return ar_id;
            }

            public void setAr_id(String ar_id) {
                this.ar_id = ar_id;
            }

            public String getAr_tl() {
                return ar_tl;
            }

            public void setAr_tl(String ar_tl) {
                this.ar_tl = ar_tl;
            }

            public String getAr_image() {
                return ar_image;
            }

            public void setAr_image(String ar_image) {
                this.ar_image = ar_image;
            }
        }

        public static class AdsEntity {
            /**
             * i_show_tpl : banner_img
             * ad_tl : 2018年1月24日 来上海见证“10大新时代商业领袖”
             * ad_name : 广告
             * ad_tm : 0
             * ad_url : http://www.jiemian.com/article/1852471.html
             * ad_img : https://img.jiemian.com/ads/20171231/151469168459915700.jpg
             * ad_open_type : 1
             * ad_size : 640*360
             * ad_wid : 7881
             * ad_aid : 5823
             * ad_position : ASHOUYE
             * ad_msurl :
             */

            private String i_show_tpl;
            private String ad_tl;
            private String ad_name;
            private String ad_tm;
            private String ad_url;
            private String ad_img;
            private String ad_open_type;
            private String ad_size;
            private String ad_wid;
            private String ad_aid;
            private String ad_position;
            private String ad_msurl;

            public String getI_show_tpl() {
                return i_show_tpl;
            }

            public void setI_show_tpl(String i_show_tpl) {
                this.i_show_tpl = i_show_tpl;
            }

            public String getAd_tl() {
                return ad_tl;
            }

            public void setAd_tl(String ad_tl) {
                this.ad_tl = ad_tl;
            }

            public String getAd_name() {
                return ad_name;
            }

            public void setAd_name(String ad_name) {
                this.ad_name = ad_name;
            }

            public String getAd_tm() {
                return ad_tm;
            }

            public void setAd_tm(String ad_tm) {
                this.ad_tm = ad_tm;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public String getAd_img() {
                return ad_img;
            }

            public void setAd_img(String ad_img) {
                this.ad_img = ad_img;
            }

            public String getAd_open_type() {
                return ad_open_type;
            }

            public void setAd_open_type(String ad_open_type) {
                this.ad_open_type = ad_open_type;
            }

            public String getAd_size() {
                return ad_size;
            }

            public void setAd_size(String ad_size) {
                this.ad_size = ad_size;
            }

            public String getAd_wid() {
                return ad_wid;
            }

            public void setAd_wid(String ad_wid) {
                this.ad_wid = ad_wid;
            }

            public String getAd_aid() {
                return ad_aid;
            }

            public void setAd_aid(String ad_aid) {
                this.ad_aid = ad_aid;
            }

            public String getAd_position() {
                return ad_position;
            }

            public void setAd_position(String ad_position) {
                this.ad_position = ad_position;
            }

            public String getAd_msurl() {
                return ad_msurl;
            }

            public void setAd_msurl(String ad_msurl) {
                this.ad_msurl = ad_msurl;
            }
        }
    }


    public static class ListEntityX implements MultiItemEntity {
        public final static int Type_kuaixun_tpl = 8;
        public final static int Type_xiaotu = 9;
        public final static int Type_pindao_hengla_tpl = 3;

        public final static int Type_ads = 4;
        public final static int Type_banner_img = 5;

        public final static int Type_h5_hengla_tpl = 6;
        public final static int Type_zhuanti_lunbo_tpl = 7;

        public final static int Type_show_img_right = 1;
        public final static int Type_show_img_top = 2;

        public final static int Type_shipin_hengla_tpl = 10;

        private String type;
        private ChannelEntity channel;
        private String i_show_tpl;
        private String i_type;
        private CountEntity count;
        private ArticleEntityX article;
        private List<ListEntity> list;
        private List<AdsEntityX> ads;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ChannelEntity getChannel() {
            return channel;
        }

        public void setChannel(ChannelEntity channel) {
            this.channel = channel;
        }

        public String getI_show_tpl() {
            return i_show_tpl;
        }

        public void setI_show_tpl(String i_show_tpl) {
            this.i_show_tpl = i_show_tpl;
        }

        public String getI_type() {
            return i_type;
        }

        public void setI_type(String i_type) {
            this.i_type = i_type;
        }

        public CountEntity getCount() {
            return count;
        }

        public void setCount(CountEntity count) {
            this.count = count;
        }

        public ArticleEntityX getArticle() {
            if (null != article)
                return article;
            else
                return new ArticleEntityX();
        }

        public void setArticle(ArticleEntityX article) {
            this.article = article;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<AdsEntityX> getAds() {
            return ads;
        }

        public void setAds(List<AdsEntityX> ads) {
            this.ads = ads;
        }

        private final static String kuaixun_tpl = "kuaixun_tpl";
        private final static String xiaotu = "xiaotu";
        private final static String pindao_hengla_tpl = "pindao_hengla_tpl";
        private final static String type_ads = "ads";
        private final static String banner_img = "banner_img";
        private final static String h5_hengla_tpl = "h5_hengla_tpl";
        private final static String zhuanti_lunbo_tpl = "zhuanti_lunbo_tpl";
        private final static String show_img_top = "show_img_top";
        private final static String show_img_top_intro="show_img_top_intro";
        private final static String show_img_right = "show_img_right";
        private final static String shipin_hengla_tpl = "shipin_hengla_tpl";
        private final static String wutu_tpl="wutu_tpl";

        @Override
        public int getItemType() {
            if (type.equals(type_ads)) {
                return Type_banner_img;
            } else {
                switch (i_show_tpl) {
                    case kuaixun_tpl:
                        return Type_kuaixun_tpl;
                    case xiaotu:
                        return Type_xiaotu;
                    case pindao_hengla_tpl:
                        return Type_pindao_hengla_tpl;
                    case h5_hengla_tpl:
                        return Type_h5_hengla_tpl;
                    case zhuanti_lunbo_tpl:
                        return Type_zhuanti_lunbo_tpl;
                    case show_img_top:
                        return Type_show_img_top;
                    case show_img_top_intro:
                        return Type_show_img_top;
                    case show_img_right:
                        return Type_show_img_right;
                    case shipin_hengla_tpl:
                        return Type_shipin_hengla_tpl;
                    case wutu_tpl:
                        return Type_show_img_right;
                    default:
                        return Type_xiaotu;
                }
            }
        }

        public static class ChannelEntity {
            /**
             * title : 快讯
             * newurl : jiemiannews://News/channel?unistr=141&id=13&url=cate/141/&name=快讯&enname=Flash
             */

            private String title;
            private String newurl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getNewurl() {
                return newurl;
            }

            public void setNewurl(String newurl) {
                this.newurl = newurl;
            }
        }

        public static class CountEntity {
            /**
             * comment : 0
             * hit : 1.8w
             * hit_status : 0
             */

            private String comment;
            private String hit;
            private String hit_status;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getHit() {
                return hit;
            }

            public void setHit(String hit) {
                this.hit = hit;
            }

            public String getHit_status() {
                return hit_status;
            }

            public void setHit_status(String hit_status) {
                this.hit_status = hit_status;
            }
        }

        public static class ArticleEntityX {
            /**
             * ar_id : 1861059
             * ar_tl : 【评论】除了道歉 如何才能解救雪乡旅游业
             * ar_pt : 1515066012
             * ar_surl : http://m.jiemian.com/article/1861059.html
             * ar_an : 郑萃颖
             * ar_cate : 交通
             * ar_image : https://img3.jiemian.com/101/original/20180104/151506085196682000_a400x300.jpg
             * ar_i_type : article
             */

            private String ar_id;
            private String ar_tl;
            private String ar_pt;
            private String ar_surl;
            private String ar_an;
            private String ar_cate;
            private String ar_image;
            private String ar_i_type;

            public String getAr_id() {
                return ar_id;
            }

            public void setAr_id(String ar_id) {
                this.ar_id = ar_id;
            }

            public String getAr_tl() {
                return ar_tl;
            }

            public void setAr_tl(String ar_tl) {
                this.ar_tl = ar_tl;
            }

            public String getAr_pt() {
                return ar_pt;
            }

            public void setAr_pt(String ar_pt) {
                this.ar_pt = ar_pt;
            }

            public String getAr_surl() {
                return ar_surl;
            }

            public void setAr_surl(String ar_surl) {
                this.ar_surl = ar_surl;
            }

            public String getAr_an() {
                return ar_an;
            }

            public void setAr_an(String ar_an) {
                this.ar_an = ar_an;
            }

            public String getAr_cate() {
                return ar_cate;
            }

            public void setAr_cate(String ar_cate) {
                this.ar_cate = ar_cate;
            }

            public String getAr_image() {
                return ar_image;
            }

            public void setAr_image(String ar_image) {
                this.ar_image = ar_image;
            }

            public String getAr_i_type() {
                return ar_i_type;
            }

            public void setAr_i_type(String ar_i_type) {
                this.ar_i_type = ar_i_type;
            }
        }

        public static class ListEntity {
            /**
             * type : article
             * article : {"ar_id":"1861216","ar_tl":"多玩YY旗下虎牙直播业务据悉寻求赴美IPO"}
             */

            private String type;
            private ArticleEntityXX article;
            private SpecialEntity special;
            private VideoEntity video;
            private AuthorEntity author;

            public AuthorEntity getAuthor() {
                return author;
            }

            public void setAuthor(AuthorEntity author) {
                this.author = author;
            }


            public VideoEntity getVideo() {
                return video;
            }

            public void setVideo(VideoEntity video) {
                this.video = video;
            }


            public SpecialEntity getSpecial() {
                return special;
            }

            public void setSpecial(SpecialEntity special) {
                this.special = special;
            }


            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public ArticleEntityXX getArticle() {
                return article;
            }

            public void setArticle(ArticleEntityXX article) {
                this.article = article;
            }

            public static class ArticleEntityXX {
                /**
                 * ar_id : 1861216
                 * ar_tl : 多玩YY旗下虎牙直播业务据悉寻求赴美IPO
                 */

                private String ar_id;
                private String ar_tl;

                private String ar_channel_name;
                private String ar_channel_enname;
                private String ar_channel_id;
                private String ar_channel_url;
                private String ar_pt;
                private String ar_image;

                public String getAr_id() {
                    return ar_id;
                }

                public void setAr_id(String ar_id) {
                    this.ar_id = ar_id;
                }

                public String getAr_tl() {
                    return ar_tl;
                }

                public void setAr_tl(String ar_tl) {
                    this.ar_tl = ar_tl;
                }

                public String getAr_channel_name() {
                    return ar_channel_name;
                }

                public void setAr_channel_name(String ar_channel_name) {
                    this.ar_channel_name = ar_channel_name;
                }

                public String getAr_channel_enname() {
                    return ar_channel_enname;
                }

                public void setAr_channel_enname(String ar_channel_enname) {
                    this.ar_channel_enname = ar_channel_enname;
                }

                public String getAr_channel_id() {
                    return ar_channel_id;
                }

                public void setAr_channel_id(String ar_channel_id) {
                    this.ar_channel_id = ar_channel_id;
                }

                public String getAr_channel_url() {
                    return ar_channel_url;
                }

                public void setAr_channel_url(String ar_channel_url) {
                    this.ar_channel_url = ar_channel_url;
                }

                public String getAr_pt() {
                    return ar_pt;
                }

                public void setAr_pt(String ar_pt) {
                    this.ar_pt = ar_pt;
                }

                public String getAr_image() {
                    return ar_image;
                }

                public void setAr_image(String ar_image) {
                    this.ar_image = ar_image;
                }
            }

            public static class SpecialEntity {
                /**
                 * id : 553
                 * tl : 习近平出席国家科学技术奖励大会并为最高奖获得者等颁奖
                 * image : https://img1.jiemian.com/101/original/20180108/151538075746016200_a750x422.jpg
                 * type_name : 专题
                 */

                private String id;
                private String tl;
                private String image;
                private String type_name;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTl() {
                    return tl;
                }

                public void setTl(String tl) {
                    this.tl = tl;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getType_name() {
                    return type_name;
                }

                public void setType_name(String type_name) {
                    this.type_name = type_name;
                }
            }

            public static class VideoEntity{

                /**
                 * v_id : 161691
                 * v_tl : 穿汉服的90后成人礼
                 * v_playtime : 360
                 * v_image : https://img3.jiemian.com/jiemian/original/20180104/151504024748452300_a500x250.jpg
                 * v_urlmobile : http://v.jiemian.com/33/01/3301587149f49a21cf353f6749995ecc_512.mp4
                 */

                private String v_id;
                private String v_tl;
                private String v_playtime;
                private String v_image;
                private String v_urlmobile;

                public String getV_id() {
                    return v_id;
                }

                public void setV_id(String v_id) {
                    this.v_id = v_id;
                }

                public String getV_tl() {
                    return v_tl;
                }

                public void setV_tl(String v_tl) {
                    this.v_tl = v_tl;
                }

                public String getV_playtime() {
                    return v_playtime;
                }

                public void setV_playtime(String v_playtime) {
                    this.v_playtime = v_playtime;
                }

                public String getV_image() {
                    return v_image;
                }

                public void setV_image(String v_image) {
                    this.v_image = v_image;
                }

                public String getV_urlmobile() {
                    return v_urlmobile;
                }

                public void setV_urlmobile(String v_urlmobile) {
                    this.v_urlmobile = v_urlmobile;
                }
            }

            public static class AuthorEntity{

                /**
                 * uid : 112801949
                 * name : 界面视频
                 * head_img : https://img.jiemian.com/userface/210x210/863/694/112801949-1474262850.jpg
                 */

                private String uid;
                private String name;
                private String head_img;

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getHead_img() {
                    return head_img;
                }

                public void setHead_img(String head_img) {
                    this.head_img = head_img;
                }
            }

        }

        public static class AdsEntityX {
            /**
             * i_show_tpl : banner_img
             * ad_tl :
             * ad_name : 广告
             * ad_tm : 1
             * ad_url : http://120.55.164.95/cordyceps150/shouye.html
             * ad_img : https://img.jiemian.com/ads/20171031/150943581095206200.jpg
             * ad_open_type : 1
             * ad_size : 640*240
             * ad_wid : 6795
             * ad_aid : 4885
             * ad_position : ASHOUYE
             * ad_msurl :
             */

            private String i_show_tpl;
            private String ad_tl;
            private String ad_name;
            private String ad_tm;
            private String ad_url;
            private String ad_img;
            private String ad_open_type;
            private String ad_size;
            private String ad_wid;
            private String ad_aid;
            private String ad_position;
            private String ad_msurl;

            public String getI_show_tpl() {
                return i_show_tpl;
            }

            public void setI_show_tpl(String i_show_tpl) {
                this.i_show_tpl = i_show_tpl;
            }

            public String getAd_tl() {
                return ad_tl;
            }

            public void setAd_tl(String ad_tl) {
                this.ad_tl = ad_tl;
            }

            public String getAd_name() {
                return ad_name;
            }

            public void setAd_name(String ad_name) {
                this.ad_name = ad_name;
            }

            public String getAd_tm() {
                return ad_tm;
            }

            public void setAd_tm(String ad_tm) {
                this.ad_tm = ad_tm;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public String getAd_img() {
                return ad_img;
            }

            public void setAd_img(String ad_img) {
                this.ad_img = ad_img;
            }

            public String getAd_open_type() {
                return ad_open_type;
            }

            public void setAd_open_type(String ad_open_type) {
                this.ad_open_type = ad_open_type;
            }

            public String getAd_size() {
                return ad_size;
            }

            public void setAd_size(String ad_size) {
                this.ad_size = ad_size;
            }

            public String getAd_wid() {
                return ad_wid;
            }

            public void setAd_wid(String ad_wid) {
                this.ad_wid = ad_wid;
            }

            public String getAd_aid() {
                return ad_aid;
            }

            public void setAd_aid(String ad_aid) {
                this.ad_aid = ad_aid;
            }

            public String getAd_position() {
                return ad_position;
            }

            public void setAd_position(String ad_position) {
                this.ad_position = ad_position;
            }

            public String getAd_msurl() {
                return ad_msurl;
            }

            public void setAd_msurl(String ad_msurl) {
                this.ad_msurl = ad_msurl;
            }
        }
    }
}
