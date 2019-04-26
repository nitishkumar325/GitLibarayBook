package e.nitishkumar.minor_project.DataModel;

public class ContributeClass {
    private String LoginName;
    private String AvtarUrl;
    private String Repos_Url;

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getAvtarUrl() {
        return AvtarUrl;
    }

    public void setAvtarUrl(String avtarUrl) {
        AvtarUrl = avtarUrl;
    }

    public String getRepos_Url() {
        return Repos_Url;
    }

    public void setRepos_Url(String repos_Url) {
        Repos_Url = repos_Url;
    }

    @Override
    public String toString() {
        return "ContributeClass{" +
                "LoginName='" + LoginName + '\'' +
                ", AvtarUrl='" + AvtarUrl + '\'' +
                ", Repos_Url='" + Repos_Url + '\'' +
                '}';
    }
}
