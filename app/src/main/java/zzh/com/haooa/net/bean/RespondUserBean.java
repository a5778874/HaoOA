package zzh.com.haooa.net.bean;

import java.util.List;

/**
 * Created by ZZH on 2018/2/20.
 */

//带着token请求注册的用户列表实体类
public class RespondUserBean {

    /**
     * action : get
     * params : {"limit":["100"]}
     * path : /users
     * uri : http://a1.easemob.com/1108180201178124/haooa/users
     * entities : [{"uuid":"27720f70-0af2-11e8-bbd6-7fe84864a5fc","type":"user","created":1517889579495,"modified":1518014189631,"username":"zzh1","activated":true},{"uuid":"b3359860-0b0b-11e8-97fa-e9893565a621","type":"user","created":1517900551398,"modified":1519117725367,"username":"zzh2","activated":true},{"uuid":"0921d850-0b12-11e8-a713-17ec355dfa07","type":"user","created":1517903272533,"modified":1517903293794,"username":"zzh3","activated":true}]
     * timestamp : 1519118896121
     * duration : 6
     * count : 3
     */

    private String action;
    private ParamsBean params;
    private String path;
    private String uri;
    private long timestamp;
    private int duration;
    private int count;
    private List<EntitiesBean> entities;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EntitiesBean> getEntities() {
        return entities;
    }

    public void setEntities(List<EntitiesBean> entities) {
        this.entities = entities;
    }

    public static class ParamsBean {
        private List<String> limit;

        public List<String> getLimit() {
            return limit;
        }

        public void setLimit(List<String> limit) {
            this.limit = limit;
        }
    }

    public static class EntitiesBean {
        /**
         * uuid : 27720f70-0af2-11e8-bbd6-7fe84864a5fc
         * type : user
         * created : 1517889579495
         * modified : 1518014189631
         * username : zzh1
         * activated : true
         */

        private String uuid;
        private String type;
        private long created;
        private long modified;
        private String username;
        private boolean activated;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getCreated() {
            return created;
        }

        public void setCreated(long created) {
            this.created = created;
        }

        public long getModified() {
            return modified;
        }

        public void setModified(long modified) {
            this.modified = modified;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isActivated() {
            return activated;
        }

        public void setActivated(boolean activated) {
            this.activated = activated;
        }
    }
}
