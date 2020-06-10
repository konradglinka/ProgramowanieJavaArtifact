package ObjectsForMapper.GIOSAirIndex;

public class C6H6IndexLevel {
    private String indexLevelName;

    private String id;

    public String getIndexLevelName ()
    {
        return indexLevelName;
    }

    public void setIndexLevelName (String indexLevelName)
    {
        this.indexLevelName = indexLevelName;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [indexLevelName = "+indexLevelName+", id = "+id+"]";
    }
}
