package pl.koziarz.l3.dns.protocol;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public enum DNSFlags {

        //EDCBA09876543210
        //0111 1000 0000 0000

        QUERY_RESPONSE(1<<15),
        OPC_QUERY(0,0x7800),
        OPC_IQUERY(1<<11,0x7800),
        OPC_STATUS(2<<11,0x7800),
        OPC_NOTIFY(4<<11,0x7800),
        OPC_UPDATE(5<<11,0x7800),

        AUTHORITATIVE_ANSWER(1<<10),
        TRUNCATION(1<<9),
        RECURSION_DESIRED(1<<8),
        RECURSION_AVAILABLE(1<<7),

        RC_NOERROR(0,0x0F),
        RC_FORMATERROR(1,0x0F),
        RC_SERVERFAILURE(2,0x0F),
        RC_NAMEERROR(3,0x0F),
        RC_NOTIMPLEMENTED(4,0x0F),
        RC_REFUSED(5,0x0F),
        RC_YXDOMAIN(6,0x0F),
        RC_YXRRSET(7,0x0F),
        RC_NXRRSET(8,0x0F),
        RC_NOTAUTH(9,0x0F),
        RC_NOTZONE(10,0x0F)
        ;

        private int val;
        private int mask;

        private DNSFlags(int val) {
                this.val=val;
                this.mask=val;
        }

        private DNSFlags(int val, int mask) {
                this.val=val;
                this.mask=mask;
        }

        public static int fromEnumSet(EnumSet<DNSFlags> flags) {
                int a=0;
                for( DNSFlags f : flags ) {
                        a |= f.val;
                }
                return a;
        }
 
	public static EnumSet<DNSFlags> toEnumSet(int bitfield) {
                ArrayList<DNSFlags> list = new ArrayList<>(DNSFlags.values().length);
                for( DNSFlags f : DNSFlags.values() ) {
                        if( (bitfield & f.mask)==f.val ) {
                                list.add(f);
                        }
                }
                return EnumSet.copyOf(list);
        }
}
