package select.spring.exquery.utils;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class ExqueryMap extends HashMap
{

    public ExqueryMap()
    {
    }

    @SuppressWarnings("unchecked")
	public Object put(Object key, Object value)
    {
        return super.put(CamelUtil.convert2CamelCase((String)key), value);
    }

    private static final long serialVersionUID = 0x5d4e6fee6eff4665L;
}
