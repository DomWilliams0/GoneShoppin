package net.chunk64.chinwe.goneshoppin.banking;

import net.chunk64.chinwe.goneshoppin.util.Config;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Account implements ConfigurationSerializable, Comparable<Account>
{
	private BigDecimal balance;
	private BankLimit limit;
	private String player;
	private boolean temporary;

	public Account(String player)
	{
		this.player = player;
		this.balance = BigDecimal.ZERO;
		this.limit = Config.DefaultLimit;
		Bank.getInstance().addAccount(this);
	}

	public Account(String player, boolean temporary)
	{
		this(player);
		this.temporary = temporary;
	}


	public void deposit(int amount)
	{
		this.balance = balance.add(new BigDecimal(amount));
	}

	public void withdraw(int amount)
	{
		this.balance = balance.subtract(new BigDecimal(amount));
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public String getName()
	{
		return player;
	}

	public BankLimit getLimit()
	{
		return limit;
	}

	public void setLimit(BankLimit limit)
	{
		this.limit = limit;
	}

	public void setBalance(Integer newBalance)
	{
		this.balance = new BigDecimal(newBalance);
	}

	@SuppressWarnings("unused")
	public Account(Map<String, Object> map)
	{
		this.player = (String) map.get("player");
		this.limit = BankLimit.valueOf((String) map.get("limit"));
		this.balance = new BigDecimal((String) map.get("balance"));
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("balance", balance.toString());
		map.put("limit", limit.toString());
		map.put("player", player);

		return map;
	}

	/**
	 * Returns true if amount can be taken out of account
	 */
	public boolean hasBalance(Integer amount)
	{
		return getBalance().doubleValue() >= amount;

	}

	public boolean isTemporary()
	{
		return temporary;
	}

	public int compareTo(Account o)
	{
		int i = balance.compareTo(o.balance);
		if (i == -1)
			return 1;
		if (i == 1)
			return -1;
		return i;

		//		if (balance.equals(o.balance))
		//			return 0;
		//
		//		int value = balance.intValue();
		//		int oValue = o.balance.intValue();
		//
		//		return value > oValue ? 1 : -1;

	}
}
