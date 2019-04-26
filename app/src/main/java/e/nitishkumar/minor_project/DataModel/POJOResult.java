package e.nitishkumar.minor_project.DataModel;

import org.json.JSONObject;

public class POJOResult {
        private String Avtar_Url;
         private String login;
         private String name;
        private String description;
        private Long watchers;
        private Long forks_count;
        private String language;
        private String Contributor_Url;
        private JSONObject jsonObject;

    public String getContributor_Url() {
        return Contributor_Url;
    }

    public void setContributor_Url(String contributor_Url) {
        Contributor_Url = contributor_Url;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getAvtar_Url() {
        return Avtar_Url;
    }

    public void setAvtar_Url(String avtar_Url) {
        Avtar_Url = avtar_Url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getWatchers() {
        return watchers;
    }

    public void setWatchers(Long watchers) {
        this.watchers = watchers;
    }

    public Long getForks_count() {
        return forks_count;
    }

    public void setForks_count(Long forks_count) {
        this.forks_count = forks_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "POJOResult{" +
                "Avtar_Url='" + Avtar_Url + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", watchers=" + watchers +
                ", forks_count=" + forks_count +
                ", language='" + language + '\'' +
                ", jsonObject=" + jsonObject +
                '}';
    }
}
