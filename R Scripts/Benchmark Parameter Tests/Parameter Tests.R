library(tidyverse)
library(dplyr)
library(ggplot2)

thr_rs <- read.csv("random_thr_rs_stats.csv")
thr_mmt <- read.csv("random_thr_mmt_stats.csv")
lr_mmt <- read.csv("random_lr_mmt_stats.csv")
lrr_mmt <- read.csv("random_lrr_mmt_stats.csv")

# mean metrics by parameter function
mean_metrics <- function(dataset){
  dataset %>% group_by(parameter) %>% summarise(meanVmMigrations = mean(numberOfMigrations),
                                                meanEnergyConsumption = mean(energy),
                                                meanSla = mean(sla),
                                                meanSLATAH = mean(slaTimePerActiveHost),
                                                meanPDM = mean(slaDegradationDueToMigration))
}

thr_rs_summ <- mean_metrics(thr_rs)
thr_mmt_summ <- mean_metrics(thr_mmt)
lr_mmt_summ <- mean_metrics(lr_mmt)
lrr_mmt_summ <- mean_metrics(lrr_mmt)

# Energy Consumption
thr_rs_energy <- thr_rs_summ %>% select(parameter, meanEnergyConsumption)
thr_mmt_energy <- thr_mmt_summ %>% select(parameter, meanEnergyConsumption)
thr_rs_energy <- c(thr_rs_energy, list(method = rep("ThrRs", length(thr_rs_energy$parameter))))
thr_mmt_energy <- c(thr_mmt_energy, list(method = rep("ThrMmt", length(thr_mmt_energy$parameter))))
thr_energy <- rbind(data.frame(thr_rs_energy), data.frame(thr_mmt_energy))
thr_energy

lr_mmt_energy <- lr_mmt_summ %>% select(parameter, meanEnergyConsumption)
lrr_mmt_energy <- lrr_mmt_summ %>% select(parameter, meanEnergyConsumption)
lr_mmt_energy <- c(lr_mmt_energy, list(method = rep("LrMmt", length(lr_mmt_energy$parameter))))
lrr_mmt_energy <- c(lrr_mmt_energy, list(method = rep("LrrMmt", length(lr_mmt_energy$parameter))))
lr_energy <- rbind(data.frame(lr_mmt_energy), data.frame(lrr_mmt_energy))
lr_energy

#visualisation
ggplot(thr_energy, aes(x=parameter, y=meanEnergyConsumption, fill=method)) + geom_bar(position="dodge", stat="identity")
ggplot(lr_energy, aes(x=parameter, y=meanEnergyConsumption, fill=method)) + geom_bar(position="dodge", stat="identity")

# VM Migration
thr_rs_migration <- thr_rs_summ %>% select(parameter, meanVmMigrations)
thr_mmt_migration <- thr_mmt_summ %>% select(parameter, meanVmMigrations)
thr_rs_migration <- c(thr_rs_migration, list(method = rep("ThrRs", length(thr_rs_migration$parameter))))
thr_mmt_migration <- c(thr_mmt_migration, list(method = rep("ThrMmt", length(thr_mmt_migration$parameter))))
thr_migration <- rbind(data.frame(thr_rs_migration), data.frame(thr_mmt_migration))
thr_migration

lr_mmt_migration <- lr_mmt_summ %>% select(parameter, meanVmMigrations)
lrr_mmt_migration <- lrr_mmt_summ %>% select(parameter, meanVmMigrations)
lr_mmt_migration <- c(lr_mmt_migration, list(method = rep("LrMmt", length(lr_mmt_migration$parameter))))
lrr_mmt_migration <- c(lrr_mmt_migration, list(method = rep("LrrMmt", length(lrr_mmt_migration$parameter))))
lr_migration <- rbind(data.frame(lr_mmt_migration), data.frame(lrr_mmt_migration))
lr_migration

# visualisation
ggplot(thr_migration, aes(x=parameter, y=meanVmMigrations, fill=method)) + geom_bar(position="dodge", stat="identity")
ggplot(lr_migration, aes(x=parameter, y=meanVmMigrations, fill=method)) + geom_bar(position="dodge", stat="identity")

# SLA
thr_rs_sla <- thr_rs_summ %>% select(parameter, meanSla, meanSLATAH, meanPDM)
thr_mmt_sla <- thr_mmt_summ %>% select(parameter, meanSla, meanSLATAH, meanPDM)
thr_rs_sla <- c(thr_rs_sla, list(method = rep("ThrRs", length(thr_rs_sla$parameter))))
thr_mmt_sla <- c(thr_mmt_sla, list(method = rep("ThrMmt", length(thr_mmt_sla$parameter))))
thr_sla <- rbind(data.frame(thr_rs_sla), data.frame(thr_mmt_sla))
thr_sla

lr_mmt_sla <- lr_mmt_summ %>% select(parameter, meanSla, meanSLATAH, meanPDM)
lrr_mmt_sla <- lrr_mmt_summ %>% select(parameter, meanSla, meanSLATAH, meanPDM)
lr_mmt_sla <- c(lr_mmt_sla, list(method = rep("LrMmt", length(lr_mmt_sla$parameter))))
lrr_mmt_sla <- c(lrr_mmt_sla, list(method = rep("LrrMmt", length(lrr_mmt_sla$parameter))))
lr_sla <- rbind(data.frame(lr_mmt_sla), data.frame(lrr_mmt_sla))
lr_sla

# visualisation
ggplot(thr_sla, aes(x=parameter, y=meanSla, fill=method)) + geom_bar(position="dodge", stat="identity")
ggplot(lr_sla, aes(x=parameter, y=meanSla, fill=method)) + geom_bar(position="dodge", stat="identity")
