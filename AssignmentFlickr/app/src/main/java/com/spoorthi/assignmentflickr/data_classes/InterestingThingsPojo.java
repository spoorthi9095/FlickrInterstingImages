package com.spoorthi.assignmentflickr.data_classes;

import java.util.List;

/**
 * Created by Spoorthi on 1/13/2018.
 */

public class InterestingThingsPojo
{

    /**
     * photos : {"page":1,"pages":100,"perpage":5,"total":500,"photo":[{"id":"38935250244","owner":"130108065@N08","secret":"092b54d6be","server":"4740","farm":5,"title":"misty.morning.rise.up","ispublic":1,"isfriend":0,"isfamily":0},{"id":"27869878289","owner":"62440012@N04","secret":"5e9929c4b1","server":"4752","farm":5,"title":"Incoming","ispublic":1,"isfriend":0,"isfamily":0},{"id":"38942505174","owner":"45571539@N06","secret":"cbf3f74e37","server":"4741","farm":5,"title":"Golden-eye","ispublic":1,"isfriend":0,"isfamily":0},{"id":"39656469601","owner":"38945681@N07","secret":"b470e84c18","server":"4696","farm":5,"title":"New Year's Sunrise","ispublic":1,"isfriend":0,"isfamily":0},{"id":"27870570079","owner":"10113436@N02","secret":"75761b8bd1","server":"4608","farm":5,"title":"Shapeshifter","ispublic":1,"isfriend":0,"isfamily":0}]}
     * stat : ok
     */

    private PhotosBean photos;
    private String stat;

    public PhotosBean getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosBean photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public static class PhotosBean {
        /**
         * page : 1
         * pages : 100
         * perpage : 5
         * total : 500
         * photo : [{"id":"38935250244","owner":"130108065@N08","secret":"092b54d6be","server":"4740","farm":5,"title":"misty.morning.rise.up","ispublic":1,"isfriend":0,"isfamily":0},{"id":"27869878289","owner":"62440012@N04","secret":"5e9929c4b1","server":"4752","farm":5,"title":"Incoming","ispublic":1,"isfriend":0,"isfamily":0},{"id":"38942505174","owner":"45571539@N06","secret":"cbf3f74e37","server":"4741","farm":5,"title":"Golden-eye","ispublic":1,"isfriend":0,"isfamily":0},{"id":"39656469601","owner":"38945681@N07","secret":"b470e84c18","server":"4696","farm":5,"title":"New Year's Sunrise","ispublic":1,"isfriend":0,"isfamily":0},{"id":"27870570079","owner":"10113436@N02","secret":"75761b8bd1","server":"4608","farm":5,"title":"Shapeshifter","ispublic":1,"isfriend":0,"isfamily":0}]
         */

        private int page;
        private int pages;
        private int perpage;
        private int total;
        private List<PhotoBean> photo;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPerpage() {
            return perpage;
        }

        public void setPerpage(int perpage) {
            this.perpage = perpage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<PhotoBean> getPhoto() {
            return photo;
        }

        public void setPhoto(List<PhotoBean> photo) {
            this.photo = photo;
        }

        public static class PhotoBean {
            /**
             * id : 38935250244
             * owner : 130108065@N08
             * secret : 092b54d6be
             * server : 4740
             * farm : 5
             * title : misty.morning.rise.up
             * ispublic : 1
             * isfriend : 0
             * isfamily : 0
             */

            private String id;
            private String owner;
            private String secret;
            private String server;
            private int farm;
            private String title;
            private int ispublic;
            private int isfriend;
            private int isfamily;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public int getFarm() {
                return farm;
            }

            public void setFarm(int farm) {
                this.farm = farm;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIspublic() {
                return ispublic;
            }

            public void setIspublic(int ispublic) {
                this.ispublic = ispublic;
            }

            public int getIsfriend() {
                return isfriend;
            }

            public void setIsfriend(int isfriend) {
                this.isfriend = isfriend;
            }

            public int getIsfamily() {
                return isfamily;
            }

            public void setIsfamily(int isfamily) {
                this.isfamily = isfamily;
            }
        }
    }
}
