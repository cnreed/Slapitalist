package ArtificialIntelligence.ArtficialIntelligenceInterface;

import Game.Company;

/**
 * Created by scout on 6/10/16.
 */
public interface IArtificialIntelligence {

    void addCompany(Company company);
    void removeCompany(Company company);
    void addMergeCount();
    void addCompaniesStartedCount();
    void createHand(int handSize);
}
