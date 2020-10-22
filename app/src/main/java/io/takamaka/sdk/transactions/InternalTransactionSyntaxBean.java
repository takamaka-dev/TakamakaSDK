package io.takamaka.sdk.transactions;

import io.takamaka.sdk.globalContext.KeyContexts;
import java.util.Objects;

/**
 *
 * @author giovanni.antino@h2tcoin.com
 */
public final class InternalTransactionSyntaxBean {

    private boolean validSyntax;
    private KeyContexts.InternalTransactionSyntaxValidity itsv;
    private String extendedMessage;

    public boolean isValidSyntax() {
        return validSyntax;
    }

    protected void setValidSyntax(boolean validSyntax) {
        this.validSyntax = validSyntax;
    }

    public KeyContexts.InternalTransactionSyntaxValidity getItsv() {
        return itsv;
    }

    protected void setItsv(KeyContexts.InternalTransactionSyntaxValidity itsv) {
        this.itsv = itsv;
    }

    public String getExtendedMessage() {
        return extendedMessage;
    }

    protected void setExtendedMessage(String extendedMessage) {
        this.extendedMessage = extendedMessage;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.validSyntax ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(this.itsv);
        hash = 23 * hash + Objects.hashCode(this.extendedMessage);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InternalTransactionSyntaxBean other = (InternalTransactionSyntaxBean) obj;
        if (this.validSyntax != other.validSyntax) {
            return false;
        }
        if (!Objects.equals(this.extendedMessage, other.extendedMessage)) {
            return false;
        }
        if (this.itsv != other.itsv) {
            return false;
        }
        return true;
    }

}
